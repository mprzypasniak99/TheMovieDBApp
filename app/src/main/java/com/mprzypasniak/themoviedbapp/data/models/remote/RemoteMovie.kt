package com.mprzypasniak.themoviedbapp.data.models.remote

import com.mprzypasniak.themoviedbapp.data.models.local.Movie

data class RemoteMovie(
    val id: Int,
    val title: String?,
    val overview: String?,
    val releaseDate: String?,
    val posterPath: String?,
    val voteAverage: Double?,
    val voteCount: Int?
) {
    fun toLocal() = Movie(
        id = id,
        title = title ?: "",
        overview = overview ?: "",
        releaseDate = releaseDate ?: "",
        posterPath = posterPath ?: "",
        voteAverage = voteAverage ?: -1.0,
        voteCount = voteCount ?: -1
    )
}
