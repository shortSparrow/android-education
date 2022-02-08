package com.example.retrofitmovie_mvvm.model

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.retrofitmovie_mvvm.data.Movie
import com.example.retrofitmovie_mvvm.service.MovieService

class MoviePagingSource (
    private val movieApiService: MovieService,
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            // Start refresh at page 1 if undefined.
            val nextPage = params.key ?: 1
            val response = movieApiService.getPopularMoviesWithPage(nextPage)

            LoadResult.Page(
                data = response.results,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = response.page?.plus(1),
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        TODO("Not yet implemented")
    }
}