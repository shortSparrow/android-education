package com.example.myfavoritemoviesmvvm.model.genre

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface GenreDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(genre: Genre)

    @Update
    suspend fun update(genre: Genre)

    @Delete
    suspend fun delete(genre: Genre)

    @Query("select * from genres_table")
    fun getGenres(): LiveData<List<Genre>>

}