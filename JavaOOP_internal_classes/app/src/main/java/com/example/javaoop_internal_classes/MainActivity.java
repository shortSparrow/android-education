package com.example.javaoop_internal_classes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

// Inner class
// Static inner class
// Local class
// Anonymous class

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Cat cat1 = new Cat(1);
        cat1.talk();

        Cat cat2 = new Cat(3);
        cat2.talk();

        Cat cat3 = new Cat(8);
        cat3.talk();

        Cat.CatMood catMood = new Cat(1).new CatMood();
        Log.i("catMood", String.valueOf(catMood.levelOfMood)); // 100

        Log.i("count", String.valueOf(Cat.countOfInstance)); // 4
        Cat.countOfInstance = 100;
        Log.i("count", String.valueOf(Cat.countOfInstance)); // 100

        Cat.CountResetter countResetter = new Cat.CountResetter();
        countResetter.resetCount();
        Log.i("count", String.valueOf(Cat.countOfInstance)); // 0


        Cat cat5 = new Cat(2);
        cat5.catchMouse(2);
        cat5.catchMouse(10);

        // Anonymous class. Еслм нам надо разово переопределить метод только для этого instance.
        Cat signingCat = new Cat(2) {
            @Override // по сути не обязательно, но лучше писать. Это явно указывает что мы перезаписываем уже существующий метод
            public void talk() {
                Log.i("talk()", "I'm signing instance of cat");
            }
        };
        signingCat.talk();

    }
}