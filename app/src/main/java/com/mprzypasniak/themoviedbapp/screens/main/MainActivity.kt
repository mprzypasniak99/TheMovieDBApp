package com.mprzypasniak.themoviedbapp.screens.main

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.mprzypasniak.themoviedbapp.base.BaseActivity
import com.mprzypasniak.themoviedbapp.data.models.Movie
import com.mprzypasniak.themoviedbapp.databinding.ActivityMainBinding
import com.mprzypasniak.themoviedbapp.screens.main.adapter.MoviesListAdapter
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

        vm.errorMessage.observe(this) {
            if (it.isNotBlank())
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }

        vm.movies.observe(this) {
            adapter.setData(it)
            binding.swipeRefreshMovies.isRefreshing = false
        }

        vm.authenticateToken()
        vm.getNowPlayingList()
    }

    private fun setUpMoviesListView() {
        with(binding) {
            adapter = MoviesListAdapter()
            adapter.onMovieClicked = ::showMovieDetails
            rvMoviesList.layoutManager = LinearLayoutManager(this@MainActivity)
            rvMoviesList.adapter = adapter
            swipeRefreshMovies.setOnRefreshListener {
                vm.getNowPlayingList()
            }
        }
    }

    private fun showMovieDetails(movie: Movie) {

    }
}