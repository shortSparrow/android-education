package com.example.myfavoritemoviesmvvm.model.movie

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MovieDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Movie)

    @Update
    suspend fun update(movie: Movie)

    @Delete
    suspend fun delete(movie: Movie)

    @Query("select * from movies_table")
    fun getMovies(): LiveData<List<Movie>>

    @Query("select * from movies_table where genre_id==:genreId")
    fun getGenreMovies(genreId: Long): LiveData<List<Movie>>
}