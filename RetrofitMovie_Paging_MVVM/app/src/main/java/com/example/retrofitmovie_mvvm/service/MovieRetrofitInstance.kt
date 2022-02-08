package com.example.retrofitmovie_mvvm.service

import com.example.retrofitmovie_mvvm.util.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MovieRetrofitInstance {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: MovieService = retrofit.create(MovieService::class.java)
}