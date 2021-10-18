package com.example.javaoop_constructors;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    Cat myCat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myCat = new Cat();
        myCat.age = 5;
        myCat.name = "Jake";
        myCat.talk();

        Cat secondCat = new Cat();
        secondCat.talk();

        Cat thirdCat = new Cat();
        thirdCat.initFields(3, "Senya");
        thirdCat.talk();

        Cat forthCat = new Cat(5, "Mukola");
        forthCat.talk();
    }
}