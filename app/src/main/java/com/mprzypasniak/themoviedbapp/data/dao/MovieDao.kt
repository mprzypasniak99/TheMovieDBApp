package com.mprzypasniak.themoviedbapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mprzypasniak.themoviedbapp.data.models.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie WHERE id IN (:ids)")
    fun getFavouritesFromStorage(ids: IntArray): List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavouriteToStorage(movie: Movie)

    @Delete
    fun deleteFavouriteFromStorage(movie: Movie)
}