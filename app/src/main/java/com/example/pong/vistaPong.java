package com.example.pong;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class vistaPong extends SurfaceView implements Runnable{
    Thread mGameThread = null;
    SurfaceHolder contenedor;
    volatile boolean estaJugando;
    boolean estaPausa = true;

    Canvas mCanvas;
    Paint pincel;
    long mFPS;

    int mTamPanX;
    int mTamPanY;

    Barra barra;
    Pelota pelota;

    int puntuacion = 0;
    int vidas = 3;

    public vistaPong(Context context, int x, int y) {
        super(context);
        mTamPanX = x;
        mTamPanY = y;
        contenedor = getHolder();
        pincel = new Paint();
        barra = new Barra(mTamPanX, mTamPanY);
        pelota = new Pelota(mTamPanX, mTamPanY);
        setupAndRestart();
    }
    public void setupAndRestart(){
        pelota.reset(mTamPanX, mTamPanY);
        if(vidas == 0) {
            puntuacion = 0;
            vidas = 3;
        }
    }
    @Override
    public void run() {
        while (estaJugando) {
            long inicioFrame = System.currentTimeMillis();
            if(!estaPausa){
                actualizar();
            }
            dibujar();
            long frameActual = System.currentTimeMillis() - inicioFrame;
            if (frameActual >= 1) {
                mFPS = 1000 / frameActual;
            }

        }

    }
    public void dibujar() {

        if (contenedor.getSurface().isValid()) {
            mCanvas = contenedor.lockCanvas();
            mCanvas.drawColor(Color.argb(0, 0, 0, 0));
            pincel.setColor(Color.argb(255, 255, 255, 255));
            mCanvas.drawRect(barra.getRect(), pincel);
            mCanvas.drawRect(pelota.getRect(), pincel);
            pincel.setColor(Color.argb(255, 255, 255, 255));
            pincel.setTextSize(40);
            mCanvas.drawText("Puntuacion: " + puntuacion + "   Lives: " + vidas, 10, 50, pincel);
            contenedor.unlockCanvasAndPost(mCanvas);
        }

    }
    public void actualizar() {
        barra.update(mFPS);
        pelota.actualizar(mFPS);
        if(RectF.intersects(barra.getRect(), pelota.getRect())) {
            pelota.setRandomVelocidad();
            pelota.invertirVelocidadY();
            pelota.quitarObsY(barra.getRect().top - 2);

            puntuacion++;
            pelota.aumentarVelocidad();
        }
        if(pelota.getRect().bottom > mTamPanY){
            pelota.invertirVelocidadY();
            pelota.quitarObsY(mTamPanY - 2);
            vidas--;
            if(vidas == 0){
                estaPausa = true;
                setupAndRestart();
            }
        }
        if(pelota.getRect().top < 0){
            pelota.invertirVelocidadY();
            pelota.quitarObsY(24);
        }

        if(pelota.getRect().left < 0){
            pelota.invertirVelocidadX();
            pelota.quitarObsX(2);
        }

        if(pelota.getRect().right > mTamPanX){
            pelota.invertirVelocidadX();
            pelota.quitarObsX(mTamPanX - 22);
        }
    }

    public void pause() {
        estaJugando = false;
        try {
            mGameThread.join();
        } catch (InterruptedException e) {
            Log.e("Error:", "...");
        }

    }
    public void resume() {
        estaJugando = true;
        mGameThread = new Thread(this);
        mGameThread.start();
    }
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                estaPausa = false;
                if(motionEvent.getX() > mTamPanX / 2){
                    barra.setMovementState(barra.RIGHT);
                }
                else{
                    barra.setMovementState(barra.LEFT);
                }
                break;
            case MotionEvent.ACTION_UP:
                barra.setMovementState(barra.STOPPED);
                break;
        }
        return true;
    }
}
