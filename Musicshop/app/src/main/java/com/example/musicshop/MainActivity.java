package com.example.musicshop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void increaseQuantity(View view) {
        TextView quantityText = findViewById(R.id.quantity);
        quantity += 1;
        quantityText.setText(String.valueOf(quantity));
    }

    public void decreaseQuantity(View view) {
        TextView quantityText = findViewById(R.id.quantity);
        if (quantity - 1 >= 0) {
            quantity -= 1;
        }
        quantityText.setText(String.valueOf(quantity));
    }
}