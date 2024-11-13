package com.mprzypasniak.themoviedbapp.data.repositories.implementations

import com.google.common.truth.Truth.assertThat
import com.mprzypasniak.themoviedbapp.data.database.FavouritesDatabase
import com.mprzypasniak.themoviedbapp.data.models.Movie
import com.mprzypasniak.themoviedbapp.data.models.responses.MovieListResponse
import com.mprzypasniak.themoviedbapp.network.api.MoviesApi
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.io.IOException

class MoviesRepositoryImplTest {
    private lateinit var apiMock: MoviesApi
    private lateinit var dbMock: FavouritesDatabase
    private lateinit var sut: MoviesRepositoryImpl

    @Before
    fun setUp() {
        apiMock = mockk()
        dbMock = mockk(relaxed= true, relaxUnitFun = true)
        sut = MoviesRepositoryImpl(apiMock, dbMock)
    }

    @Test
    fun `getMoviesList returns successful Result instance when api call succeeds`() = runBlocking {
        val response = MovieListResponse(
            page = 1,
            results = listOf(
                Movie(
                    id = 1,
                    title = "Test",
                    overview ="Test",
                    releaseDate = "01.01.2001",
                    posterPath = "",
                    voteAverage = 4.4,
                    voteCount = 3)),
            totalPages = 12)

        every { apiMock.getMoviesNowPlayingList(any()).execute() } returns Response.success(response)

        val result = sut.getMoviesList("en_US")

        verify { apiMock.getMoviesNowPlayingList("en_US") }
        confirmVerified(apiMock)

        assertThat(result.isSuccess)
            .isTrue()
        assertThat(result.isFailure)
            .isFalse()
        assertThat(result.getOrNull())
            .isEqualTo(response)
        assertThat(result.exceptionOrNull())
            .isNull()
    }

    @Test
    fun `getMoviesList returns failed Result instance when api cannot connect with server`() =
        runBlocking {
            val error = IOException("Test error message")

            every { apiMock.getMoviesNowPlayingList(any()).execute() } throws error

            val result = sut.getMoviesList("en_US")

            verify { apiMock.getMoviesNowPlayingList("en_US") }
            confirmVerified(apiMock)

            assertThat(result.isSuccess)
                .isFalse()
            assertThat(result.isFailure)
                .isTrue()
            assertThat(result.getOrNull())
                .isNull()
            assertThat(result.exceptionOrNull())
                .hasMessageThat()
                .isEqualTo("Test error message")
        }

    @Test
    fun `addFavourite sends movie data for saving in database`() = runBlocking {
        val testMovie = Movie(
            id = 1,
            title = "Test",
            overview ="Test",
            releaseDate = "01.01.2001",
            posterPath = "",
            voteAverage = 4.4,
            voteCount = 3
        )

        sut.addFavourite(testMovie)

        coVerify { dbMock.movieDao().insertFavouriteToStorage(match {
            it.id == testMovie.id && it.isFavourite
        }) }
        confirmVerified(dbMock)
    }

    @Test
    fun `deleteFavourite sends movie data for deleting from database`() = runBlocking {
        val testMovie = Movie(
            id = 1,
            title = "Test",
            overview ="Test",
            releaseDate = "01.01.2001",
            posterPath = "",
            voteAverage = 4.4,
            voteCount = 3
        )

        sut.deleteFavourite(testMovie)

        coVerify { dbMock.movieDao().deleteFavouriteFromStorage(match {
            it.id == testMovie.id && !it.isFavourite
        }) }
        confirmVerified(dbMock)
    }

    @Test
    fun `getFavouriteMoviesFlow gets list of favourites from database`() {
        sut.getFavouriteMoviesFlow()

        verify { dbMock.movieDao().getFavouritesFromStorage() }
        confirmVerified(dbMock)
    }
}