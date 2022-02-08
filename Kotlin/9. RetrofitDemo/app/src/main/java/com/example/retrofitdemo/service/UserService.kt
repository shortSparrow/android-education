package com.example.retrofitdemo.service

import com.example.retrofitdemo.data.User
import retrofit2.Call
import retrofit2.http.GET


interface UserService {

    @GET("/users")
    fun getUsers(): Call<ArrayList<User>>

}