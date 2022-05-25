package com.example.pong;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class vistaPong extends SurfaceView implements Runnable{
    Thread mGameThread = null;

    SurfaceHolder mOurHolder;

    volatile boolean mPlaying;

    boolean mPaused = true;

    Canvas mCanvas;
    Paint mPaint;

    long mFPS;


    int mScreenX;
    int mScreenY;


    Bat mBat;


    Ball mBall;


    int mScore = 0;


    int mLives = 3;
    public vistaPong(Context context, int x, int y) {


        super(context);


        mScreenX = x;
        mScreenY = y;


        mOurHolder = getHolder();
        mPaint = new Paint();

        mBat = new Bat(mScreenX, mScreenY);

        mBall = new Ball(mScreenX, mScreenY);



        setupAndRestart();

    }

    public void setupAndRestart(){


        mBall.reset(mScreenX, mScreenY);

        if(mLives == 0) {
            mScore = 0;
            mLives = 3;
        }

    }
    @Override
    public void run() {
        while (mPlaying) {

             long startFrameTime = System.currentTimeMillis();


            if(!mPaused){
                update();
            }


            draw();


            long timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1) {
                mFPS = 1000 / timeThisFrame;
            }

        }

    }

    public void update() {


        mBat.update(mFPS);

        mBall.update(mFPS);


        if(RectF.intersects(mBat.getRect(), mBall.getRect())) {
            mBall.setRandomXVelocity();
            mBall.reverseYVelocity();
            mBall.clearObstacleY(mBat.getRect().top - 2);

            mScore++;
            mBall.increaseVelocity();
        }

        if(mBall.getRect().bottom > mScreenY){
            mBall.reverseYVelocity();
            mBall.clearObstacleY(mScreenY - 2);


            mLives--;

            if(mLives == 0){
                mPaused = true;
                setupAndRestart();
            }
        }

        if(mBall.getRect().top < 0){
            mBall.reverseYVelocity();
            mBall.clearObstacleY(24);
        }

        if(mBall.getRect().left < 0){
            mBall.reverseXVelocity();
            mBall.clearObstacleX(2);
        }

        if(mBall.getRect().right > mScreenX){
            mBall.reverseXVelocity();
            mBall.clearObstacleX(mScreenX - 22);
        }
    }

    public void draw() {


        if (mOurHolder.getSurface().isValid()) {

            mCanvas = mOurHolder.lockCanvas();


            mCanvas.drawColor(Color.argb(255, 120, 197, 87));


            mPaint.setColor(Color.argb(255, 255, 255, 255));


            mCanvas.drawRect(mBat.getRect(), mPaint);


            mCanvas.drawRect(mBall.getRect(), mPaint);



            mPaint.setColor(Color.argb(255, 255, 255, 255));


            mPaint.setTextSize(40);
            mCanvas.drawText("Score: " + mScore + "   Lives: " + mLives, 10, 50, mPaint);


            mOurHolder.unlockCanvasAndPost(mCanvas);
        }

    }

    public void pause() {
        mPlaying = false;
        try {
            mGameThread.join();
        } catch (InterruptedException e) {
            Log.e("Error:", "joining thread");
        }

    }



    public void resume() {
        mPlaying = true;
        mGameThread = new Thread(this);
        mGameThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN:

                mPaused = false;

                if(motionEvent.getX() > mScreenX / 2){
                    mBat.setMovementState(mBat.RIGHT);
                }
                else{
                    mBat.setMovementState(mBat.LEFT);
                }

                break;

            case MotionEvent.ACTION_UP:

                mBat.setMovementState(mBat.STOPPED);
                break;
        }
        return true;
    }
}
