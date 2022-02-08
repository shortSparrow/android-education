package com.example.javaoop_interfaces;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Printable printable = new Lion();
        printable.print();
        ((Lion) printable).move(); // привели printable к классу Lion, так как только у него есть метод move, а у интерфейса Printable его нет


        printAnyObj(new Lion()); // можно, так как Lion реализует интерфейс printable
//        printAnyObj(new Puma()); // низя, так как Puma не имплементирует метод printable

        Movable.someMethod(); // static someMethod
        Log.i("d", String.valueOf(Movable.speedOfMove));  // 100
    }

    // Не будь интерейсов, нам бы пришлось дклать перегрузку метода printAnyObj для нужного нам класса
    public void printAnyObj(Printable printable) {

    }
}