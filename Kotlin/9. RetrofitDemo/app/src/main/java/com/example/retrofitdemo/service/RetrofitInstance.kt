package com.example.retrofitdemo.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com"

    var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    var service: UserService = retrofit.create(UserService::class.java)
}