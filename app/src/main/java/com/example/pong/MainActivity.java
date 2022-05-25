package com.example.pong;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

public class MainActivity extends AppCompatActivity {

    VistaPong vistaPong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        Point tamanio = new Point();
        display.getSize(tamanio);
        vistaPong = new VistaPong(this, tamanio.x, tamanio.y);
        setContentView(vistaPong);
    }
    @Override
    protected void onResume() {
        super.onResume();
        vistaPong.resume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        vistaPong.pause();
    }
}