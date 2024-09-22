package com.mprzypasniak.themoviedbapp.data.repositories

import com.mprzypasniak.themoviedbapp.data.models.Movie
import com.mprzypasniak.themoviedbapp.data.models.responses.MovieListResponse
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    suspend fun getMoviesList(languageTag: String): Result<MovieListResponse>
    suspend fun addFavourite(movie: Movie)
    suspend fun deleteFavourite(movie: Movie)
    fun getFavouriteMoviesFlow() : Flow<List<Int>>
}