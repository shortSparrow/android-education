package com.example.dependencyingection

import javax.inject.Inject

class CarEngine @Inject constructor(private val engineFlap: EngineFlap) {
    val test = engineFlap.mess
}
