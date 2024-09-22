package com.mprzypasniak.themoviedbapp.screens.main

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.mprzypasniak.themoviedbapp.base.BaseActivity
import com.mprzypasniak.themoviedbapp.data.models.Movie
import com.mprzypasniak.themoviedbapp.databinding.ActivityMainBinding
import com.mprzypasniak.themoviedbapp.screens.main.adapter.MovieListItem
import com.mprzypasniak.themoviedbapp.screens.main.adapter.MoviesListAdapter
import com.mprzypasniak.themoviedbapp.screens.movie_details.MovieDetailsDialog
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Locale

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {
    override val inflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate
    override val vm: MainViewModel by viewModel()

    private lateinit var adapter: MoviesListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm.languageTag = Locale.getDefault().toLanguageTag()

        setUpMoviesListView()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.uiState.collect { state ->
                    handleErrorUpdate(state.errorMessage)
                    handleMoviesListUpdate(state)

                    binding.swipeRefreshMovies.isRefreshing = state.isFetchingMovies
                }
            }
        }

        vm.initView()
    }

    private fun handleErrorUpdate(errorMessage: String) {
        if (errorMessage.isNotBlank()) {
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    private fun handleMoviesListUpdate(state: MainUiState) {
        val listItems = state.movies.map { movie ->
            MovieListItem(movie, movie.id in state.favourites)
        }
        adapter.setData(listItems)
    }

    private fun setUpMoviesListView() {
        with(binding) {
            adapter = MoviesListAdapter()
            adapter.onMovieClicked = ::showMovieDetails
            adapter.onFavouriteClicked = vm::toggleFavouriteOnMovie
            rvMoviesList.layoutManager = LinearLayoutManager(this@MainActivity)
            rvMoviesList.adapter = adapter
            swipeRefreshMovies.setOnRefreshListener {
                vm.getNowPlayingList()
            }
        }
    }

    private fun showMovieDetails(movie: Movie) {
        vm.selectMovie(movie)
        MovieDetailsDialog().show(supportFragmentManager, MovieDetailsDialog.TAG)
    }
}