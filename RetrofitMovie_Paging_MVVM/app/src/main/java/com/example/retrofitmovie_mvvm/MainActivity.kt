package com.example.retrofitmovie_mvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitmovie_mvvm.model.MovieListViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // initialize viewmodel in activity to get all fragments access
        val viewModel = ViewModelProvider(this)[MovieListViewModel::class.java]
    }
}