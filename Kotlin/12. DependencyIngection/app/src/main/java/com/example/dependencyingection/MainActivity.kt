package com.example.dependencyingection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.internal.DaggerGenerated

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var carComponent: CarComponent? = DaggerCarComponent.create()
        val car = carComponent?.getCar()
        car?.move()

    }
}