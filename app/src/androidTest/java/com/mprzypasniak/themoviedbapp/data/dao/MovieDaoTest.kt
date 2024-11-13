package com.mprzypasniak.themoviedbapp.data.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.mprzypasniak.themoviedbapp.data.database.FavouritesDatabase
import com.mprzypasniak.themoviedbapp.data.models.MovieLocalModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.jvm.Throws

@RunWith(AndroidJUnit4::class)
@SmallTest
class MovieDaoTest {
    private lateinit var movieDao: MovieDao
    private lateinit var db: FavouritesDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, FavouritesDatabase::class.java).build()
        movieDao = db.movieDao()
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertingMovieSendsUpdateToListFlow() = runBlocking {
        val movie = MovieLocalModel(id = 1, isFavourite = true)
        val moviesFlow = movieDao.getFavouritesFromStorage()

        val latch = CountDownLatch(2)
        val job = async(Dispatchers.IO) {
            moviesFlow.collect {
                if (it.isEmpty()) {
                    assertThat(latch.count)
                        .isEqualTo(2)

                    latch.countDown()
                } else {
                    assertThat(it)
                        .containsExactly(movie.id)

                    latch.countDown()
                }
            }
        }

        movieDao.insertFavouriteToStorage(movie)

        latch.await(200, TimeUnit.MILLISECONDS)
        job.cancelAndJoin()

        assertThat(latch.count)
            .isEqualTo(0)
    }

    @Test
    @Throws(Exception::class)
    fun deletingMovieSendsUpdateToListFlow() = runBlocking {
        val movie = MovieLocalModel(id = 1, isFavourite = true)

        movieDao.insertFavouriteToStorage(movie)
        movieDao.deleteFavouriteFromStorage(movie)

        val moviesFlow = movieDao.getFavouritesFromStorage()

        val latch = CountDownLatch(1)
        val job = async(Dispatchers.IO) {
            moviesFlow.collect {
                assertThat(it).doesNotContain(movie.id)

                latch.countDown()
            }
        }

        latch.await(200, TimeUnit.MILLISECONDS)
        job.cancelAndJoin()

        assertThat(latch.count)
            .isEqualTo(0)
    }
}