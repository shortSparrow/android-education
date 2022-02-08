package com.example.retrofitmovie_mvvm.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.retrofitmovie_mvvm.MovieAdapter
import com.example.retrofitmovie_mvvm.R
import com.example.retrofitmovie_mvvm.data.Movie
import com.example.retrofitmovie_mvvm.model.MovieListViewModel
import com.example.retrofitmovie_mvvm.util.Utils

class FragmentMovieList : Fragment() {
    private lateinit var viewOfLayout: View
    lateinit var recyclerView: RecyclerView
    lateinit var movieAdapter: MovieAdapter

    lateinit var swipeRefreshLayout: SwipeRefreshLayout

    companion object {
        fun newInstance() = FragmentMovieList()
    }

    private val viewModel: MovieListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewOfLayout = inflater.inflate(R.layout.fragment_movie_list, container, false)
        return viewOfLayout
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
        requireContext()


        viewModel.getPopularMovies()
        viewModel.movieList.observe(viewLifecycleOwner) {
            fillRecyclerView(it)
        }

        swipeRefreshLayout = viewOfLayout?.findViewById(R.id.swiperefresh)
        swipeRefreshLayout.setColorSchemeResources(R.color.purple_700)
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.getPopularMovies()
        }
    }

    private fun fillRecyclerView(movieList: ArrayList<Movie>) {
        recyclerView = viewOfLayout.findViewById(R.id.recyclerView)
        movieAdapter = MovieAdapter(movieList)

        val columns = Utils.calculateNoOfColumns(requireContext(), 170f)
        recyclerView.layoutManager = GridLayoutManager(context, columns)
        recyclerView.adapter = movieAdapter
        swipeRefreshLayout.isRefreshing = false

        movieAdapter.onItemClick = { movie ->
            viewModel.selectableMovie = movie
            Navigation.findNavController(recyclerView).navigate(R.id.fullMovieFragment)
        }

    }
}