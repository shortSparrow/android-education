package com.example.activitylifecycle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        if (savedInstanceState != null) {
            textView.setText(savedInstanceState.getString("textToBundle"));
        }

        Log.d("Lifecycle method", "onCreate");
        textView.append("onCreate()" + "\n");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Lifecycle method", "onStart");
        textView.append("onStart()" + "\n");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Lifecycle method", "onPause");
        textView.append("onPause()" + "\n");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Lifecycle method", "onResume");
        textView.append("onResume()" + "\n");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Lifecycle method", "onStop");
        textView.append("onStop()" + "\n");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Lifecycle method", "onDestroy");
        textView.append("onDestroy()" + "\n");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("Lifecycle method", "onRestart");
        textView.append("onRestart()" + "\n");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.d("onSaveInstanceState", "onSaveInstanceState ");
        textView.append("onSaveInstanceState()" + "\n");

        // при поворте экрана наша activity разрушается и создается снова. Мы сохраням м данные чтот бы не потерять их.
        // Но при клике назад это не поможет сохнаится данные, для этого надо использовать
        outState.putString("textToBundle", textView.getText().toString());
    }
}