package com.example.retrofitmovie_mvvm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.retrofitmovie_mvvm.data.Movie
import com.example.retrofitmovie_mvvm.databinding.MovieItemBinding

class MovieAdapter(private val movieList: ArrayList<Movie>) :
    RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder>() {
    inner class MovieAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = MovieItemBinding.bind(itemView)
    }

    var onItemClick: ((Movie) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MovieAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieAdapterViewHolder, position: Int) {
        val movie = movieList[position]
        with(holder) {
            binding.movie = movie

            Glide
                .with(binding.movieItemImage.context)
                .load("https://image.tmdb.org/t/p/w500/" + movie.posterPath)
                .centerCrop()
                .placeholder(R.drawable.progress_circle)
                .into(binding.movieItemImage);
        }

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(movie)
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

}