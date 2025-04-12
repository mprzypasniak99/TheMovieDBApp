package com.mprzypasniak.themoviedbapp.data.models

import com.google.gson.annotations.SerializedName
import com.mprzypasniak.themoviedbapp.ext.PosterSize


data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int
) {
    fun getPosterUrl(size: PosterSize): String =
        StringBuilder("https://image.tmdb.org/t/p/")
            .append(size.symbol)
            .append(posterPath)
            .toString()

}
