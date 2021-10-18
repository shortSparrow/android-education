package com.example.javaoop_constructors;

import android.util.Log;

public class Cat {
    int age;
    String name;

    // constructor. Укзанываем его, если хотим изменить поведение при инициализации класса
    public Cat() {
        // Зададим дефотные значения. Сейчас они age=0; name:null
        age = 2;
        name ="unknown cat";
    }

    // создали другой конструктор, но с другими пропсами. Консрукторов может быть сколько угодно, но они должны отличаться пропсами
    // по факту это тоже самое что и метод initFields
    public Cat(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public void talk() {
        Log.i("talk()", "Meow! My name is " + name + " and I am " + age + " years old.");
    }

    public void initFields(int age, String catName) {
        this.age = age; // this указади что бы не было путаницы между age=age
        name = catName;
    }
}
