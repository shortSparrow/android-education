package com.example.pizzarecipes;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class FullPizzaInfo extends AppCompatActivity {
    TextView titleView;
    TextView consistView;
    TextView priceView;
    ImageView pizzaImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pizza_full_item);

        titleView = findViewById(R.id.recipeTitle);
        consistView = findViewById(R.id.pizzaConsist);
        priceView = findViewById(R.id.pizzaPrice);
        pizzaImageView = findViewById(R.id.pizzaImage);

        PizzaRecyclerItem pizzaItem = getIntent().getParcelableExtra("pizzaItem");
        Log.i("pizzaRecyclerItem", String.valueOf(pizzaItem.getPrice()));

        titleView.setText(pizzaItem.getTitle());
        consistView.setText(pizzaItem.getShortDescription());
        priceView.setText(String.valueOf(pizzaItem.getPrice()));
        Picasso.get().load(pizzaItem.getImageURI()).into(pizzaImageView);
    }

    public void makeOrder(View view) {
        Toast toast = Toast.makeText(getApplicationContext(),
                "Слава мені, я король!", Toast.LENGTH_LONG);
        toast.show();
    }
}
