package com.example.myfavoritemoviesmvvm

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myfavoritemoviesmvvm.databinding.FragmentMovieListBinding
import com.example.myfavoritemoviesmvvm.model.MovieAdapter
import com.example.myfavoritemoviesmvvm.model.add_edit.AddEditViewModel
import com.example.myfavoritemoviesmvvm.model.movie.Movie
import com.example.myfavoritemoviesmvvm.model.movie.MovieRepository
import com.example.myfavoritemoviesmvvm.model.movie.MovieViewModel
import com.example.myfavoritemoviesmvvm.model.movie.MovieViewModelFactory
import com.example.myfavoritemoviesmvvm.model.genre.Genre
import com.example.myfavoritemoviesmvvm.model.genre.GenreRepository
import com.example.myfavoritemoviesmvvm.model.genre.GenreViewModel
import com.example.myfavoritemoviesmvvm.model.genre.GenreViewModelFactory


class MovieList : Fragment() {
    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var movieAdapter: MovieAdapter

    private val addEditViewModel: AddEditViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val genreRepository = activity?.let { AppDatabase.getDatabase(it).genreDao }
            ?.let { GenreRepository(it) }

        val movieRepository = activity?.let { AppDatabase.getDatabase(it).movieDao }
            ?.let { MovieRepository(it) }

        val genreFactory = genreRepository?.let { GenreViewModelFactory(it) }
        val movieFactory = movieRepository?.let { MovieViewModelFactory(it) }

        val genreViewModel = genreFactory?.let {
            ViewModelProvider(
                this,
                it
            )
        }?.get(GenreViewModel::class.java)

        movieViewModel = movieFactory?.let {
            ViewModelProvider(
                this,
                it
            )
        }?.get(MovieViewModel::class.java)!!

        genreViewModel?.getGenres()?.observe(viewLifecycleOwner) {
            showInSpinner(it)
        }

        binding.fab.setOnClickListener {
            onFabClick(it)
        }

        binding.genreSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                onSelectedItemSpinner(p0, p1, p2, p3)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun onFabClick(view: View) {
        addEditViewModel.behavior = "create"
        addEditViewModel.movie = Movie()
        view.findNavController().navigate(R.id.addEditFragment)
    }

    fun onSelectedItemSpinner(
        parent: AdapterView<*>?,
        view: View?,
        position: Int,
        id: Long
    ) {
        val selectedGenre: Genre = parent?.getItemAtPosition(position) as Genre
        loadGenreMovies(selectedGenre.id)
    }

    private fun loadGenreMovies(genreId: Long) {
        movieViewModel.getGenreMovies(genreId).observe(viewLifecycleOwner) {
            movieViewModel.movieList = it as ArrayList
            loadRecyclerView()
        }
    }

    private fun showInSpinner(genreList: List<Genre>) {
        val genreArrayAdapter: ArrayAdapter<Genre>? = activity?.let {
            ArrayAdapter<Genre>(
                it.applicationContext,
                R.layout.spinner_item,
                genreList
            )
        }

        genreArrayAdapter?.setDropDownViewResource(R.layout.spinner_item)
        binding.spinnerAdapter = genreArrayAdapter
    }

    private fun loadRecyclerView() {
        recyclerView = binding.movieRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(binding.root.context)
        recyclerView.hasFixedSize()

        movieAdapter = MovieAdapter(addEditViewModel)
        movieAdapter.setMovieList(movieViewModel.movieList)
        recyclerView.adapter = movieAdapter

        swipeMovie()
    }

    private fun swipeMovie() {
        ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder
                ): Boolean {
                    return false // true if moved, false otherwise
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val movie = movieViewModel.movieList[viewHolder.adapterPosition]
                    movieViewModel.delete(movie)
                }
            }).attachToRecyclerView(recyclerView)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}