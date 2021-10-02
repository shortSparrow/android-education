package com.example.musicshop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    int quantity = 0;
    Spinner spinner;
    String[] spinnerList;
    ArrayAdapter spinnerAdapter;
    HashMap goodsMap;
    String goodsName;
    double price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        createSpinner();
        createGoodsList();
    }

    void createSpinner() {
        spinnerList = new String[]{"guitar", "drums", "keyboard"};
        spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
    }

    void createGoodsList() {
        goodsMap = new HashMap();
        goodsMap.put("guitar", 500.0);
        goodsMap.put("drums", 250.0);
        goodsMap.put("keyboard", 150.0);
    }

    public void increaseQuantity(View view) {
        TextView quantityText = findViewById(R.id.quantity);
        quantity += 1;
        quantityText.setText(String.valueOf(quantity));
        TextView priceTextView = findViewById(R.id.priceTextView);
        priceTextView.setText(String.valueOf(quantity * price));
    }

    public void decreaseQuantity(View view) {
        TextView quantityText = findViewById(R.id.quantity);
        if (quantity - 1 >= 0) {
            quantity -= 1;
        }
        quantityText.setText(String.valueOf(quantity));
        TextView priceTextView = findViewById(R.id.priceTextView);
        priceTextView.setText(String.valueOf(quantity * price));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        goodsName = spinner.getSelectedItem().toString();
        price = (double) goodsMap.get(goodsName);
        TextView priceTextView = findViewById(R.id.priceTextView);
        ImageView instrumentImage = findViewById(R.id.instrumentImage);
        priceTextView.setText(String.valueOf(quantity * price));

        switch (goodsName) {
            case "guitar":
                instrumentImage.setImageResource(R.drawable.guitar);
                break;
            case "drums":
                instrumentImage.setImageResource(R.drawable.drums);
                break;
            case "keyboard":
                instrumentImage.setImageResource(R.drawable.keyboard);
                break;
            default:
                instrumentImage.setImageResource(R.drawable.guitar);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}