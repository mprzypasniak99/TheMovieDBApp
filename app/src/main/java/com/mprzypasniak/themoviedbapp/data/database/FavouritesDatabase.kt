package com.mprzypasniak.themoviedbapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mprzypasniak.themoviedbapp.data.dao.MovieDao
import com.mprzypasniak.themoviedbapp.data.models.Movie

@Database(entities = [Movie::class], version = 1)
abstract class FavouritesDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
}