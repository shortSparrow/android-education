package com.example.myfavoritemoviesmvvm.model.movie

import androidx.lifecycle.LiveData

interface MovieRepositoryInterface {
    suspend fun insert(movie: Movie)

    suspend fun update(movie: Movie)

    suspend fun delete(movie: Movie)

    fun getMovies(): LiveData<List<Movie>>

    fun getGenreMovies(genreId: Long): LiveData<List<Movie>>
}