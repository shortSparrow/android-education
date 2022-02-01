package com.example.myfavoritemoviesmvvm.model.genre

import androidx.lifecycle.LiveData

interface GenreRepositoryInterface {
    suspend fun insert(genre: Genre)

    suspend fun update(genre: Genre)

    suspend fun delete(genre: Genre)

    fun getGenres(): LiveData<List<Genre>>
}