package com.mprzypasniak.themoviedbapp.screens.main

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mprzypasniak.themoviedbapp.base.BaseActivity
import com.mprzypasniak.themoviedbapp.data.models.Movie
import com.mprzypasniak.themoviedbapp.ui.screens.HomeScreen
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Locale

class MainActivity : BaseActivity<MainViewModel>() {
    override val vm: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm.languageTag = Locale.getDefault().toLanguageTag()

        vm.initView()
    }

    override val view: @Composable () -> Unit = {
        val uiState = vm.uiState.collectAsStateWithLifecycle()
        HomeScreen(
            uiState = uiState.value,
            onRefresh = vm::getNowPlayingList,
            onMovieSelected = ::showMovieDetails,
            onFavouriteToggle = vm::toggleFavouriteOnMovie,
            modifier = Modifier
        )
    }


    private fun showMovieDetails(movie: Movie) {
        vm.selectMovie(movie)
    }
}