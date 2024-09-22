package com.mprzypasniak.themoviedbapp.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mprzypasniak.themoviedbapp.data.models.Movie
import com.mprzypasniak.themoviedbapp.data.repositories.AuthenticationRepository
import com.mprzypasniak.themoviedbapp.data.repositories.MoviesRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val authRepo: AuthenticationRepository,
    private val moviesRepo: MoviesRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    var languageTag: String = ""

    private var authenticationJob: Job? = null
    private var fetchNowPlayingJob: Job? = null
    private var toggleFavouriteJob: Job? = null
    private var favouritesFlowJob: Job? = null

    fun initView() {
        authenticateToken()
        getNowPlayingList()
        observeFavouritesList()
    }

    private fun authenticateToken() {
        authenticationJob?.cancel()
        authenticationJob = viewModelScope.launch {
            authRepo.authenticateToken()
                .onFailure {
                    _uiState.update { state ->
                        state.copy(errorMessage = it.localizedMessage ?: "Unknown error")
                    }
                }
        }
    }

    fun getNowPlayingList() {
        fetchNowPlayingJob?.cancel()
        fetchNowPlayingJob = viewModelScope.launch {
            _uiState.update { it.copy(isFetchingMovies = true) }
            moviesRepo.getMoviesList(languageTag)
                .onSuccess {
                    _uiState.update { state ->
                        state.copy(movies = it.results, isFetchingMovies = false)
                    }
                }
        }
    }

    fun selectMovie(movie: Movie) {
        _uiState.update {
            it.copy(selectedMovieIndex = it.movies.indexOf(movie))
        }
    }

    fun toggleFavouriteOnMovie(movie: Movie, isFavourite: Boolean) {
        toggleFavouriteJob?.cancel()
        toggleFavouriteJob = viewModelScope.launch {
            if (isFavourite) {
                moviesRepo.addFavourite(movie)
            } else {
                moviesRepo.deleteFavourite(movie)
            }
        }
    }

    private fun observeFavouritesList() {
        favouritesFlowJob?.cancel()
        favouritesFlowJob = viewModelScope.launch {
            moviesRepo.getFavouriteMoviesFlow().distinctUntilChanged().collect { value ->
                _uiState.update {
                    it.copy(favourites = value)
                }
            }
        }
    }
}