package com.example.javaoop_interfaces;

import android.util.Log;

public interface Movable {
    int speedOfMove = 100; // by default is is static

    void move();

    static void someMethod() {
        Log.i("someMethod()", "static someMethod");
    }
}
