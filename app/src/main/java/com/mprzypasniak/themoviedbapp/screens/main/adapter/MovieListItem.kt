package com.mprzypasniak.themoviedbapp.screens.main.adapter

import com.mprzypasniak.themoviedbapp.data.models.Movie

data class MovieListItem(
    val movie: Movie,
    val isFavourite: Boolean
)
