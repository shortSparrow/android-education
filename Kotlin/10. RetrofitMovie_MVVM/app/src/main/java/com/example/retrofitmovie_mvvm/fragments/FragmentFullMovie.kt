package com.example.retrofitmovie_mvvm.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.retrofitmovie_mvvm.R
import com.example.retrofitmovie_mvvm.databinding.FragmentFullMovieBinding
import com.example.retrofitmovie_mvvm.model.MovieListViewModel


class FragmentFullMovie : Fragment() {
    private val sharedViewModel: MovieListViewModel by activityViewModels()

    private var _binding: FragmentFullMovieBinding? = null
    private val binding get() = _binding!!


    companion object {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFullMovieBinding.inflate(inflater, container, false)
        binding.movie = sharedViewModel.selectableMovie

        val posterUrl = "https://image.tmdb.org/t/p/w500/" + sharedViewModel.selectableMovie.posterPath
        Glide
            .with(this)
            .load(posterUrl)
            .centerCrop()
            .into(binding.fullMoviePoster);

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}