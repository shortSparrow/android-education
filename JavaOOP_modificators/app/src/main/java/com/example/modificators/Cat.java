package com.example.modificators;

import android.util.Log;

public class Cat {
    static int numberOfLegs = 4;
    final static boolean canBreath = true;
    static int countOfInstance = 0;

    public Cat() {
        countOfInstance++;
    }

    final public void talk() {
        Log.i("talk()", "I'm a cat");
    }
}
