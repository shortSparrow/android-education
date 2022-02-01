package com.example.myfavoritemoviesmvvm.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.myfavoritemoviesmvvm.R
import com.example.myfavoritemoviesmvvm.model.add_edit.AddEditViewModel
import com.example.myfavoritemoviesmvvm.databinding.MovieItemBinding
import com.example.myfavoritemoviesmvvm.model.movie.Movie
import com.google.android.material.snackbar.Snackbar

class MovieAdapter(private val addEditViewModel: AddEditViewModel) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    private var movieList = ArrayList<Movie>()
    fun setMovieList(list: ArrayList<Movie>) {
        movieList = list
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(var binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onItemClick(binding.root, movieList[adapterPosition])
                }
            }
        }
    }

    fun onItemClick(view: View, movie: Movie) {
        addEditViewModel.behavior = "update"
        addEditViewModel.movie = movie
        view.findNavController().navigate(R.id.addEditFragment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val movieItemBinding =
            MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(movieItemBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList[position]
        holder.binding.movie = movie // передаємо movie у нашу розмітку
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

}