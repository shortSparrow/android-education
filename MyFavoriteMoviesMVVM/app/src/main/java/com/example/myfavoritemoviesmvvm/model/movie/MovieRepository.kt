package com.example.myfavoritemoviesmvvm.model.movie

import androidx.lifecycle.LiveData

class MovieRepository(private val dao: MovieDAO) : MovieRepositoryInterface {
    override suspend fun insert(movie: Movie) {
        dao.insert(movie)
    }

    override suspend fun update(movie: Movie) {
        dao.update(movie)
    }

    override suspend fun delete(movie: Movie) {
        dao.delete(movie)
    }

    override fun getMovies(): LiveData<List<Movie>> {
        return dao.getMovies()
    }

    override fun getGenreMovies(genreId: Long): LiveData<List<Movie>> {
        return dao.getGenreMovies(genreId)
    }

}