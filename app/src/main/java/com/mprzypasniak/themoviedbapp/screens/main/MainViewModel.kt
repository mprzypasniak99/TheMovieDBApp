package com.mprzypasniak.themoviedbapp.screens.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
}