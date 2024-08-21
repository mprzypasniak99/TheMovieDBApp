package com.mprzypasniak.themoviedbapp.network.api

import com.mprzypasniak.themoviedbapp.data.models.Movie
import com.mprzypasniak.themoviedbapp.data.models.responses.MovieListResponse
import com.mprzypasniak.themoviedbapp.network.ApiConstants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {
    @GET(ApiConstants.NOW_PLAYING_LIST)
    fun getMoviesNowPlayingList(
        @Query("language") language: String,
//        @Query("page") page: Int
        ): Call<MovieListResponse>
}