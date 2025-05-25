package com.mprzypasniak.themoviedbapp.screens.main

import android.os.Bundle
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mprzypasniak.themoviedbapp.R
import com.mprzypasniak.themoviedbapp.base.BaseActivity
import com.mprzypasniak.themoviedbapp.data.models.Movie
import com.mprzypasniak.themoviedbapp.navigation.Details
import com.mprzypasniak.themoviedbapp.navigation.Home
import com.mprzypasniak.themoviedbapp.ui.screens.HomeScreen
import com.mprzypasniak.themoviedbapp.ui.screens.MovieDetailsScreen
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Locale

class MainActivity : BaseActivity<MainViewModel>() {
    override val vm: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm.languageTag = Locale.getDefault().toLanguageTag()

        vm.initView()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override val view: @Composable () -> Unit = {
        val uiState = vm.uiState.collectAsStateWithLifecycle()
        val navController = rememberNavController()

        Scaffold(
            topBar = {
                TopAppBar(title = {
                    Text(stringResource(R.string.app_name))
                })
            }
        ) { paddingValues ->
            NavHost(
                navController,
                startDestination = Home,
                modifier = Modifier.padding(paddingValues)
            ) {
                composable<Home> {
                    HomeScreen(
                        uiState = uiState.value,
                        onRefresh = vm::getNowPlayingList,
                        onMovieSelected = {
                            showMovieDetails(it)
                            navController.navigate(route = Details)
                        },
                        onFavouriteToggle = vm::toggleFavouriteOnMovie
                    )
                }
                composable<Details> {
                    MovieDetailsScreen(
                        uiState = uiState.value,
                        onFavouriteToggle = vm::toggleFavouriteOnMovie,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }


    private fun showMovieDetails(movie: Movie) {
        vm.selectMovie(movie)
    }
}