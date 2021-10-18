package com.example.javaoop_interfaces;

import android.util.Log;

public interface Printable {
    default void print() {
        Log.i("print()", "default implementation");
    }
}
