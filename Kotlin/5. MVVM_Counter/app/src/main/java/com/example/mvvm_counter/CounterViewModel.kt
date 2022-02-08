package com.example.mvvm_counter

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CounterViewModel : ViewModel() {
    val TAG = "CounterViewModel"
    var counter = 0

    val currentCount: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>(0)
    }

    init {
        Log.i(TAG, "GameViewModel created!")
    }

    fun increaseValue() {
        counter++
        currentCount.value = counter
    }

    fun decreaseValue() {
        counter--
        currentCount.value = counter
    }

    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "GameViewModel destroyed!")
    }
}