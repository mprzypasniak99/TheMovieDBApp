package com.mprzypasniak.themoviedbapp.data.models

import com.google.gson.annotations.SerializedName


data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int
)
