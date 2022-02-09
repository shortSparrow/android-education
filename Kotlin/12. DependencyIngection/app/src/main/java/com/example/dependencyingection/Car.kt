package com.example.dependencyingection

import android.util.Log
import javax.inject.Inject

class Car @Inject constructor(
    private var carEngine: CarEngine,
    private var carBattery: CarBattery,
    private var carChassis: CarChassis
) {

    fun move() {
        Log.d("Care_move", "Car is moving " + carEngine.test.toString())
    }
}