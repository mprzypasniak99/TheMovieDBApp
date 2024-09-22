package com.mprzypasniak.themoviedbapp.screens.main

import com.mprzypasniak.themoviedbapp.data.models.Movie

data class MainUiState(
    val errorMessage: String = "",
    val movies: List<Movie> = emptyList(),
    val favourites: List<Int> = emptyList(),
    val selectedMovieIndex: Int? = null,
    val isFetchingMovies: Boolean = false
) {
    val selectedMovie: Movie?
        get() = selectedMovieIndex?.let { movies[it] }
}
