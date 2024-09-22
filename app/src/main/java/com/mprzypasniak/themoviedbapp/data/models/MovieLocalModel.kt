package com.mprzypasniak.themoviedbapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieLocalModel(
    @PrimaryKey val id: Int,
    val isFavourite: Boolean
)