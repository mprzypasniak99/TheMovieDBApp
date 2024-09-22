package com.mprzypasniak.themoviedbapp.data.repositories.implementations

import com.mprzypasniak.themoviedbapp.base.BaseRepository
import com.mprzypasniak.themoviedbapp.data.database.FavouritesDatabase
import com.mprzypasniak.themoviedbapp.data.models.Movie
import com.mprzypasniak.themoviedbapp.data.models.MovieLocalModel
import com.mprzypasniak.themoviedbapp.data.repositories.MoviesRepository
import com.mprzypasniak.themoviedbapp.network.api.MoviesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class MoviesRepositoryImpl(
    private val api: MoviesApi,
    private val db: FavouritesDatabase
): BaseRepository(), MoviesRepository {
    override suspend fun getMoviesList(languageTag: String) = result {
        resolveNetworkResponse {
            api.getMoviesNowPlayingList(languageTag).execute()
        }
    }

    override suspend fun addFavourite(movie: Movie) {
        withContext(Dispatchers.IO) {
            val model = MovieLocalModel(movie.id, true)
            db.movieDao().insertFavouriteToStorage(model)
        }
    }

    override suspend fun deleteFavourite(movie: Movie) {
        withContext(Dispatchers.IO) {
            val model = MovieLocalModel(movie.id, false)
            db.movieDao().deleteFavouriteFromStorage(model)
        }
    }

    override fun getFavouriteMoviesFlow(): Flow<List<Int>> =
        db.movieDao().getFavouritesFromStorage()

}