package com.mprzypasniak.themoviedbapp.data.repositories

import com.mprzypasniak.themoviedbapp.data.models.Movie
import com.mprzypasniak.themoviedbapp.data.models.responses.MovieListResponse

interface MoviesRepository {
    fun getMoviesList(languageTag: String): Result<MovieListResponse>
}