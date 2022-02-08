package com.example.retrofitmovie_mvvm.service

import com.example.retrofitmovie_mvvm.data.PopularMovieResponse
import com.example.retrofitmovie_mvvm.util.API_KEY
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    @GET("movie/popular?api_key=$API_KEY")
    fun getPopularMovies(): Call<PopularMovieResponse>
}