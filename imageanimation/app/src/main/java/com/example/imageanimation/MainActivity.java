package com.example.imageanimation;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    boolean isCatImageVisible = true;
    ImageView catImage;
    ImageView fritterImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        catImage = findViewById(R.id.catImageView);
        fritterImage = findViewById(R.id.fritterImageView);

        fritterImage.setScaleX(0);
        fritterImage.setScaleY(0);
    }

    public void toggleImage(View view) {

        int time = 1000;
        int deg = 360;


        if (isCatImageVisible) {
            catImage.animate().alpha(0).scaleX(0).scaleY(0).rotation(catImage.getRotation() + deg).setDuration(time);
            fritterImage.animate().alpha(1).scaleX(1).scaleY(1).rotation(fritterImage.getRotation() + deg).setDuration(time);
            isCatImageVisible = !isCatImageVisible;
        } else {
            catImage.animate().alpha(1).scaleX(1).scaleY(1).rotation(catImage.getRotation() + deg).setDuration(time);
            fritterImage.animate().alpha(0).scaleX(0).scaleY(0).rotation(fritterImage.getRotation() + deg).setDuration(time);
            isCatImageVisible = !isCatImageVisible;
        }
    }
}