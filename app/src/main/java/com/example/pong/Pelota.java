package com.example.pong;

import android.graphics.RectF;

import java.util.Random;

public class Pelota {

    private RectF mCoordenadasRec;
    private float mVelcidadX;
    private float mVelicidadY;
    private float pelotaAncho;
    private float pelotaAlto;

    public Pelota(int screenX, int screenY){
        pelotaAncho = screenX / 100;
        pelotaAlto = pelotaAncho;
        mVelicidadY = screenY / 4;
        mVelcidadX = mVelicidadY;
        mCoordenadasRec = new RectF();
    }
    public RectF getRect(){
        return mCoordenadasRec;
    }
    public void actualizar(long fps){
        mCoordenadasRec.left = mCoordenadasRec.left + (mVelcidadX / fps);
        mCoordenadasRec.top = mCoordenadasRec.top + (mVelicidadY / fps);
        mCoordenadasRec.right = mCoordenadasRec.left + pelotaAncho;
        mCoordenadasRec.bottom = mCoordenadasRec.top - pelotaAlto;
    }
    public void invertirVelocidadX(){
        mVelicidadY = -mVelicidadY;
    }

    public void invertirVelocidadY(){
        mVelcidadX = -mVelcidadX;
    }

    public void setRandomVelocidad(){
        Random generator = new Random();
        int answer = generator.nextInt(2);
        if(answer == 0){
            invertirVelocidadX();
        }
    }
    public void aumentarVelocidad(){
        mVelcidadX = mVelcidadX + mVelcidadX / 10;
        mVelicidadY = mVelicidadY + mVelicidadY / 10;
    }
    public void quitarObsY(float y){
        mCoordenadasRec.bottom = y;
        mCoordenadasRec.top = y - pelotaAlto;
    }

    public void quitarObsX(float x){
        mCoordenadasRec.left = x;
        mCoordenadasRec.right = x + pelotaAncho;
    }

    public void reset(int x, int y){
        mCoordenadasRec.left = x / 2;
        mCoordenadasRec.top = y - 20;
        mCoordenadasRec.right = x / 2 + pelotaAncho;
        mCoordenadasRec.bottom = y - 20 - pelotaAlto;
    }
}
