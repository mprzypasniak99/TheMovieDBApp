package com.mprzypasniak.themoviedbapp.data.repositories

import com.mprzypasniak.themoviedbapp.data.models.local.Movie

interface MoviesRepository {
    fun getMoviesList(): List<Movie>
}