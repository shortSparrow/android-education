package com.example.sharedpreferancies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);

//        SharedPreferences sharedPreferences = this.getPreferences(); // xml файл только для этой activity
        SharedPreferences sharedPreferences = this.getSharedPreferences("mySharedPreferences", Context.MODE_PRIVATE); // xml файл для всего приложения

        SharedPreferences.Editor editor =  sharedPreferences.edit();
        editor.putString("Hello", "world");
//        editor.remove("Hello"); // delete Hello key
//        editor.clear() // delete all data
        editor.apply();

        textView.append(sharedPreferences.getString("Hello", "no values"));
    }
}