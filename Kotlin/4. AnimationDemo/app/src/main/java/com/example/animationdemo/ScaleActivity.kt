package com.example.animationdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView

class ScaleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scale)
    }

    fun startAnimation(view: android.view.View) {
//        startScaleAnimation()
//        startTranslateAnimation()
        startTranslateAnimationDynamic()
    }

    private fun startScaleAnimation() {
        val scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_animation)
        scaleAnimation.duration = 1000
        val imageView = findViewById<ImageView>(R.id.scalableImage)
        imageView.startAnimation(scaleAnimation)
    }

    fun startTranslateAnimation() {
        val translateAnimation = AnimationUtils.loadAnimation(this, R.anim.translate_animation)
        translateAnimation.duration = 1000
        val imageView = findViewById<ImageView>(R.id.scalableImage)
        imageView.startAnimation(translateAnimation)
    }

    fun startTranslateAnimationDynamic() {
        val imageView = findViewById<ImageView>(R.id.scalableImage)
        imageView.animate().translationYBy(200f).duration = 2000
    }

}