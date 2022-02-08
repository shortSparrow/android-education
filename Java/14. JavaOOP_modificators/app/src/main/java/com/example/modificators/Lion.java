package com.example.modificators;

import android.util.Log;

public class Lion extends Cat{
    public void talk() { // мы не может переопределить этот метод в наследнике, так как в меттоде родителя стоит модификатор final
        Log.i("talk()", "I'm a Lion");
    }
}
