package com.example.javaoop_interfaces;

import android.util.Log;

public class Puma extends Cat implements Movable {
    @Override
    public void move() {
        Log.i("move()", "move Puma");
    }
}
