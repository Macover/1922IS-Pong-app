package com.example.pong;

import android.graphics.RectF;

public class Barra {

    private RectF mRect;

    private float mLength;
    private float mHeight;


    private float mXCoord;


    private float mYCoord;



    private float mBatSpeed;

    public final int STOPPED = 0;
    public final int LEFT = 1;
    public final int RIGHT = 2;


    private int mBatMoving = STOPPED;


    private int mScreenX;
    private int mScreenY;

    public Barra(int x, int y){

        mScreenX = x;
        mScreenY = y;


        mLength = mScreenX / 8;


        mHeight = mScreenY / 25;


        mXCoord = mScreenX / 2;
        mYCoord = mScreenY - 20;

        mRect = new RectF(mXCoord, mYCoord, mXCoord + mLength, mYCoord + mHeight);


        mBatSpeed = mScreenX;

    }


    public RectF getRect(){
        return mRect;
    }

    public void setMovementState(int state){
        mBatMoving = state;
    }

    public void update(long fps){

        if(mBatMoving == LEFT){
            mXCoord = mXCoord - mBatSpeed / fps;
        }

        if(mBatMoving == RIGHT){
            mXCoord = mXCoord + mBatSpeed / fps;
        }


        if(mRect.left < 0){ mXCoord = 0; } if(mRect.right > mScreenX){
            mXCoord = mScreenX -

                    (mRect.right - mRect.left);
        }

        mRect.left = mXCoord;
        mRect.right = mXCoord + mLength;
    }
}
