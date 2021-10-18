package com.example.modificators;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Cat cat1 = new Cat();
        Log.i("numberOfLegs", "number of legs cat1 is: " + cat1.numberOfLegs); // 4
        cat1.numberOfLegs--; // но так обращатся не правильно, лучше делать так  Cat.numberOfLegs--. Тогда будет радобать автокомплит
        Log.i("numberOfLegs", "number of legs cat1 is: " + cat1.numberOfLegs); // 3

        Cat cat2 = new Cat();
        Log.i("numberOfLegs", "number of legs cat2 is: " + cat2.numberOfLegs); // 3

        Log.i("countOfInstance", String.valueOf(Cat.countOfInstance)); // 2

        Cat.canBreath = false; // Мы не может менять значение этого свойства

        Lion lion = new Lion();
        lion.talk();
    }
}