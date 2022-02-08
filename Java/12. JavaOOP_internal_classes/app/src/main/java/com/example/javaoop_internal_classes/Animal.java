package com.example.javaoop_internal_classes;

// abstract class
// Например у нас будет метод draw обим для всех потомков класса Animal, но у каждого он свой, тогда что бы не создавать этот метод в самом Animal, мы пишем конструкцию с abstract. Эта конструкция возможна только в абстактном классе, а он в свою очередь не может иметь instance
public abstract class Animal {
    int someValue;

    abstract void draw();
}
