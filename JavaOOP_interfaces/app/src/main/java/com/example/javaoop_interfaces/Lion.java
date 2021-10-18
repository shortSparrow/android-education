package com.example.javaoop_interfaces;

import android.util.Log;

public class Lion extends Cat implements Movable, Printable {

    @Override
    public void move() {
        Log.i("move()", "move Lion");
    }

    // Так как print имеет дефолтную имплементацию, то на не обязательно его объявлять в самом классе. Но при желании мы можем его без проблем переопределить
//    @Override
//    public void print() {
//
//    }

}
