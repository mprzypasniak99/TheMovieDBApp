package com.mprzypasniak.themoviedbapp.data.repositories.implementations

import com.mprzypasniak.themoviedbapp.base.BaseRepository
import com.mprzypasniak.themoviedbapp.data.database.FavouritesDatabase
import com.mprzypasniak.themoviedbapp.data.models.Movie
import com.mprzypasniak.themoviedbapp.data.models.responses.MovieListResponse
import com.mprzypasniak.themoviedbapp.data.repositories.MoviesRepository
import com.mprzypasniak.themoviedbapp.network.api.MoviesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class MoviesRepositoryImpl(
    private val api: MoviesApi,
    private val db: FavouritesDatabase
): BaseRepository(), MoviesRepository {
    override fun getMoviesList(languageTag: String) = result {
        val result: MovieListResponse = resolveNetworkResponse {
            api.getMoviesNowPlayingList(languageTag).execute()
        }

        val favourites = db.movieDao().getFavouritesFromStorage(result.results.map(Movie::id).toIntArray())

        val moviesWithFavourites = result.results.map {
            val favouritesIds = favourites.associateBy(Movie::id)
            return@map if (it.id in favouritesIds) {
                it.copy(isFavourite = favouritesIds[it.id]?.isFavourite == true)
            } else {
                it
            }
        }

        result.copy(results = moviesWithFavourites)
    }

    override fun addFavourite(movie: Movie) {
        runBlocking(Dispatchers.IO) {
            db.movieDao().insertFavouriteToStorage(movie)
        }
    }

    override fun deleteFavourite(movie: Movie) {
        runBlocking(Dispatchers.IO) {
            db.movieDao().deleteFavouriteFromStorage(movie)
        }
    }
}