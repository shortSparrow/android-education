package com.example.retrofitmovie_mvvm.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.retrofitmovie_mvvm.data.Movie
import com.example.retrofitmovie_mvvm.data.PopularMovieResponse
import com.example.retrofitmovie_mvvm.service.MovieRetrofitInstance
import com.example.retrofitmovie_mvvm.service.MovieService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieListViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    var movieList = MutableLiveData<ArrayList<Movie>>()

     lateinit var selectableMovie : Movie

    fun getPopularMovies() {
        val movieService: MovieService = MovieRetrofitInstance.service
        val call: Call<PopularMovieResponse> = movieService.getPopularMovies()

        call.enqueue(object : Callback<PopularMovieResponse> {
            override fun onResponse(
                call: Call<PopularMovieResponse>,
                response: Response<PopularMovieResponse>
            ) {
                val res: PopularMovieResponse? = response.body()
                if (res != null && res.results.size > 0) {
                    movieList.postValue(res.results)
                }

            }

            override fun onFailure(call: Call<PopularMovieResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}