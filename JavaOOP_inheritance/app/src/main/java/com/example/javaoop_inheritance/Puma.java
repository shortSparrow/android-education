package com.example.javaoop_inheritance;

import android.util.Log;

public class Puma extends Cat {

    private String pumaHelloText;

    public Puma () {
        super();
        pumaHelloText = "I'm a cool cat!";
        name = "Puma";
        age = 3;
    }

    private String  createPumaTalkText() {
        return pumaHelloText + " My name is " + name + " and I'm " + age + " years old";
    }

    public void talk() {
        Log.i("talk()", createPumaTalkText());
    }


    public Puma(String name, int age) {
        super(name, age);
    }

    public void voice() {
        Log.i("voice()", "R-r-r!");
    }
}
