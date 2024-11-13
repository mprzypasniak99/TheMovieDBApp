package com.mprzypasniak.themoviedbapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mprzypasniak.themoviedbapp.data.models.MovieLocalModel
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT id FROM movieLocalModel")
    fun getFavouritesFromStorage(): Flow<List<Int>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteToStorage(movie: MovieLocalModel)

    @Delete
    suspend fun deleteFavouriteFromStorage(movie: MovieLocalModel)
}