package com.android.layoutworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.android.layoutworkout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var ActivityMainBinding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

//        ActivityMainBinding.button.setOnClickListener { changeText() }
    }

    private fun changeText() {
//        ActivityMainBinding.textView3.text = "Data binding"
    }
}