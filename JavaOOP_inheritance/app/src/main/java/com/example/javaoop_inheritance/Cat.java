package com.example.javaoop_inheritance;

import android.util.Log;

public class Cat extends Animal {
    String name;
    int age;

    public Cat (String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Cat() {

    }

    public void talk() {
        Log.i("talk()", "Meow! My name is " + name + " and " + age + " year old.");
    }

    public void talk(int age) {
        Log.i("talk()", "Meow! I'm " + age + " year old.");
    }


    public void voice() {
        Log.i("voice()", "Meow!");
    }
}
