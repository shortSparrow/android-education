package com.example.hellojava;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void changeText(View view) {
        TextView mainText = findViewById(R.id.mainText);
        mainText.setText("Hello java");
        mainText.setTextSize(20);
        mainText.setBackgroundColor(Color.GREEN);
    }
}