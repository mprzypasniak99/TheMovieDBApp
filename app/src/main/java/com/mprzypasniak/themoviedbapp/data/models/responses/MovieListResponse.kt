package com.mprzypasniak.themoviedbapp.data.models.responses

import com.google.gson.annotations.SerializedName
import com.mprzypasniak.themoviedbapp.data.models.Movie

data class MovieListResponse(
    val page: Int,
    val results: List<Movie>,
    @SerializedName("total_pages") val totalPages: Int
)