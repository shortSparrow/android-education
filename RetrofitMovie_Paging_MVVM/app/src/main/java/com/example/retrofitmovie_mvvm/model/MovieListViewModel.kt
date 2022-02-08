package com.example.retrofitmovie_mvvm.model

import androidx.lifecycle.*
import androidx.paging.*
import com.example.retrofitmovie_mvvm.data.Movie
import com.example.retrofitmovie_mvvm.service.MovieRetrofitInstance
import com.example.retrofitmovie_mvvm.service.MovieService
import kotlinx.coroutines.flow.Flow

class MovieListViewModel : ViewModel() {
    lateinit var selectableMovie: Movie

    val movies: Flow<PagingData<Movie>> = Pager(PagingConfig(pageSize = 20)) {
        val movieService: MovieService = MovieRetrofitInstance.service
        MoviePagingSource(movieService)
    }.flow
        .cachedIn(viewModelScope)
}