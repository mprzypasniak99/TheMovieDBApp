package com.mprzypasniak.themoviedbapp.screens.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.mprzypasniak.themoviedbapp.data.models.Movie
import com.mprzypasniak.themoviedbapp.data.repositories.AuthenticationRepository
import com.mprzypasniak.themoviedbapp.data.repositories.MoviesRepository

class MainViewModel(
    private val authRepo: AuthenticationRepository,
    private val moviesRepo: MoviesRepository
): ViewModel() {
    private val _errorMessage = MutableLiveData("")
    private val _movies = MutableLiveData<List<Movie>>()

    val errorMessage: LiveData<String>
        get() = _errorMessage
    val movies: LiveData<List<Movie>>
        get() = _movies

    val _selectedMovie = MutableLiveData<Movie?>(null)
    val selectedMovie: LiveData<Movie?>
        get() = _selectedMovie

    var languageTag: String = ""

    fun authenticateToken() {
        authRepo.authenticateToken()
            .onFailure {
                _errorMessage.postValue(it.message)
            }
    }

    fun getNowPlayingList() {
        moviesRepo.getMoviesList(languageTag)
            .onSuccess {
                _movies.postValue(it.results)
            }
    }

    fun selectMovie(movie: Movie) {
        _selectedMovie.postValue(movie)
    }

    fun toggleFavouriteOnMovie(movie: Movie, isFavourite: Boolean) {
        val updatedMovie = movie.copy(isFavourite = isFavourite)
        if (isFavourite) {
            moviesRepo.addFavourite(updatedMovie)
        } else {
            moviesRepo.deleteFavourite(updatedMovie)
        }

        val updatedList = _movies.value?.map { if (it.id == updatedMovie.id) updatedMovie else it } ?: emptyList()
        if (_selectedMovie.value?.id == updatedMovie.id) selectMovie(updatedMovie)
        _movies.postValue(updatedList)
    }
}