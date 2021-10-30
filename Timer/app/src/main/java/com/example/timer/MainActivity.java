package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Handler handler = new Handler();
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                Log.d("runnable", "2 sec passed");
//                handler.postDelayed(this, 2000);
//            }
//        };
//
//        handler.post(runnable);

        CountDownTimer myTimer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d("runnable", "left " + millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {
                Log.d("runnable", "END");
            }
        };

        myTimer.start();
    }
}