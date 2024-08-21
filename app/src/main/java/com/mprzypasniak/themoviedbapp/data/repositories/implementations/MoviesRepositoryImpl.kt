package com.mprzypasniak.themoviedbapp.data.repositories.implementations

import com.mprzypasniak.themoviedbapp.base.BaseRepository
import com.mprzypasniak.themoviedbapp.data.repositories.MoviesRepository
import com.mprzypasniak.themoviedbapp.network.api.MoviesApi

class MoviesRepositoryImpl(private val api: MoviesApi): BaseRepository(), MoviesRepository {
    override fun getMoviesList(languageTag: String) = result {
        api.getMoviesNowPlayingList(languageTag).execute()
    }
}