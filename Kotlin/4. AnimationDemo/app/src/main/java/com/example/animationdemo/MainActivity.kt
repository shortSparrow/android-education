package com.example.animationdemo

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imageView = findViewById<ImageView>(R.id.animationImageView)
        val gradientOverlay = findViewById<LinearLayout>(R.id.gradientOverlay)

        val background = imageView.background as AnimationDrawable
        background.setEnterFadeDuration(3000)
        background.setExitFadeDuration(1000)
        background.start()

        val backgroundOverlay = gradientOverlay.background as AnimationDrawable
        backgroundOverlay.setEnterFadeDuration(3000)
        backgroundOverlay.setExitFadeDuration(1000)
        backgroundOverlay.start()
    }

    fun openScaleActivity(view: android.view.View) {
        val intent = Intent(this, ScaleActivity::class.java)
        startActivity(intent)
    }
}