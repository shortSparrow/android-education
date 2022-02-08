package com.example.javaoop_inheritance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Cat myCat = new Cat("Bob", 3);
        myCat.numberOfLegs = 4;
        Log.i("numberOfLegs", String.valueOf(myCat.numberOfLegs));
        myCat.breath();
        myCat.voice();

        Puma myPuma = new Puma("Fry", 5);
        myPuma.talk();
        myPuma.talk(11);

        myPuma.voice();
    }
}