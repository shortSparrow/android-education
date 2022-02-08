package com.android.firstkotlinapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        helloTextView.setText("Hi") // warning, because we can use attribute text from xml itself
        helloTextView.text = "Hello Senya"
    }
}