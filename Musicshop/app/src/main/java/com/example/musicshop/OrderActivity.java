package com.example.musicshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class OrderActivity extends AppCompatActivity {

    TextView CustomerNameText;
    TextView GoodsNameText;
    TextView QuantityText;
    TextView PriceText;
    TextView OrderPriceText;

    String totalOrderInfo;

    String[] addresses = {"formobileprima@gmail.com"};
    String subject = "music order";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        CustomerNameText = findViewById(R.id.customerName);
        GoodsNameText = findViewById(R.id.goodsName);
        QuantityText = findViewById(R.id.quantity);
        PriceText = findViewById(R.id.price);
        OrderPriceText = findViewById(R.id.orderPrice);

        Intent orderIntent = getIntent();
        String userName = orderIntent.getStringExtra("userName");
        String goodsName = orderIntent.getStringExtra("goodsName");
        int quantity = orderIntent.getIntExtra("quantity", 0);
        double price = orderIntent.getDoubleExtra("price", 0);
        String orderPrice = orderIntent.getStringExtra("orderPrice");

        totalOrderInfo = "Customer: " + userName + "\n" + "Products: " + goodsName + "\n" + "Quantity: " + quantity + "\n" + "Total price " + price;

        CustomerNameText.setText(userName);
        GoodsNameText.setText(goodsName);
        QuantityText.setText(String.valueOf(quantity));
        PriceText.setText(String.valueOf(price));
        OrderPriceText.setText(orderPrice);
    }

    public void sendEmail(View view) {

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
//        intent.setType("*/*"); // show all list available app
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, totalOrderInfo);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }
}