package com.example.retrofitmovie_mvvm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.retrofitmovie_mvvm.data.Movie
import com.example.retrofitmovie_mvvm.databinding.MovieItemBinding

class MovieAdapter : PagingDataAdapter<Movie, MovieAdapter.MovieViewHolder>(MovieComparator){

    var onItemClick: ((Movie) -> Unit)? = null

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)!!
        holder.itemView.findViewById<TextView>(R.id.movie_item_title).text = movie.title
        holder.itemView.findViewById<TextView>(R.id.movie_item_release_date).text = movie.releaseDate

        val image = holder.itemView.findViewById<ImageView>(R.id.movie_item_image)
        Glide
            .with(image)
            .load("https://image.tmdb.org/t/p/w500/" + movie.posterPath)
            .centerCrop()
            .placeholder(R.drawable.progress_circle)
            .into(image);

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(movie)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_item, parent, false)
        )
    }

    class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view)

    object MovieComparator : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            // Id is unique.
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}