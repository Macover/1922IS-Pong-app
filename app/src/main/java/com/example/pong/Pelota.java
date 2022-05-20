package com.example.pong;

import android.graphics.RectF;

public class Pelota {

    private RectF cordenadasRectangulo;
    private float velocidadX;
    private float velocidadY;
    private float pelotaAncho;
    private float pelotaAlto;

    public Pelota(int pantallaX, int pantallaY){
        //La pelota es una centésima parte del ancho de la pantalla.
        this.pelotaAncho = pantallaX / 100;
        this.pelotaAlto = this.pelotaAncho;
        /* La pelota va a 1/4 de altura de la pantalla
        cada segundo.*/
        this.velocidadX = this.velocidadY;
        this.velocidadY = pantallaY / 4;
        
    }

}
