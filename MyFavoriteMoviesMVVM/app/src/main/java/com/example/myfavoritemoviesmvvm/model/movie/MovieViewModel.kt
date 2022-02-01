package com.example.myfavoritemoviesmvvm.model.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {
    lateinit var movieList: ArrayList<Movie>

    fun insert(movie: Movie) {
        viewModelScope.launch {
            repository.insert(movie)
        }
    }

    fun update(movie: Movie) {
        viewModelScope.launch {
            repository.insert(movie)
        }
    }

    fun delete(movie: Movie) {
        viewModelScope.launch {
            repository.delete(movie)
        }
    }

    fun getMovies(): LiveData<List<Movie>> {
        return repository.getMovies()
    }

    fun getGenreMovies(genreId: Long): LiveData<List<Movie>> {
        return repository.getGenreMovies(genreId)
    }
}