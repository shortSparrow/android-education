package com.example.myfavoritemoviesmvvm.model.genre

import androidx.lifecycle.LiveData


class GenreRepository(private val dao: GenreDAO) : GenreRepositoryInterface {
    override suspend  fun insert(genre: Genre) {
        dao.insert(genre)
    }

    override suspend fun update(genre: Genre) {
        dao.update(genre)
    }

    override suspend fun delete(genre: Genre) {
        dao.delete(genre)
    }

    override fun getGenres(): LiveData<List<Genre>> {
       return dao.getGenres()
    }
}