package com.mprzypasniak.themoviedbapp.screens.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mprzypasniak.themoviedbapp.data.models.Movie
import com.mprzypasniak.themoviedbapp.databinding.ItemMovieBinding
import com.mprzypasniak.themoviedbapp.ext.PosterSize
import com.mprzypasniak.themoviedbapp.ext.loadPoster
import java.util.Locale

class MoviesListAdapter: RecyclerView.Adapter<MoviesListAdapter.ViewHolder>() {
    private var movies = ArrayList<MovieListItem>()
    lateinit var onMovieClicked: (Movie) -> Unit
    lateinit var onFavouriteClicked: (Movie, Boolean) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return movies.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    fun setData(data: List<MovieListItem>) {
        movies.clear()
        movies.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemMovieBinding): RecyclerView.ViewHolder(binding.root) {
        private lateinit var movie: MovieListItem

        fun bind(item: MovieListItem) {
            movie = item

            binding.tvMovieTitle.text = movie.movie.title
            binding.ivMovieIcon.loadPoster(movie.movie.posterPath, PosterSize.W92)
            binding.tvScore.text = String.format(Locale.getDefault(), "%.2f", movie.movie.voteAverage)
            binding.cbIsFavourite.isChecked = movie.isFavourite

            binding.cbIsFavourite.setOnClickListener {
                onFavouriteClicked(movie.movie, !movie.isFavourite)
            }

            binding.root.setOnClickListener {
                onMovieClicked(movie.movie)
            }
        }
    }
}