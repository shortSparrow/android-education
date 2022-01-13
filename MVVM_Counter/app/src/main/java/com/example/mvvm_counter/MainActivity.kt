package com.example.mvvm_counter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm_counter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: CounterViewModel
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )

        viewModel = ViewModelProvider(this).get(CounterViewModel::class.java)

//        val currentCount = viewModel.currentCount
        binding.increaseButton.setOnClickListener {
            viewModel.increaseValue()
//            currentCount.value = currentCount.value?.plus(1)
        }

        binding.decreaseButton.setOnClickListener {
//            currentCount.value = currentCount.value?.minus(1)
            viewModel.decreaseValue()
        }

        val counterObserver = Observer<Int> { newCounter ->
            binding.counter = newCounter
        }
        viewModel.currentCount.observe(this, counterObserver)
    }

}