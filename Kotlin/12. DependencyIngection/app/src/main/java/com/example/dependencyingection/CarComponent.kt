package com.example.dependencyingection

import dagger.Component
import javax.inject.Singleton

@Singleton @Component
interface CarComponent {
    fun getCar() : Car
}