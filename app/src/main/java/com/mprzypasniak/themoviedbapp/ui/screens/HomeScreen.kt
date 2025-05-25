package com.mprzypasniak.themoviedbapp.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mprzypasniak.themoviedbapp.data.models.Movie
import com.mprzypasniak.themoviedbapp.screens.main.MainUiState
import com.mprzypasniak.themoviedbapp.screens.main.adapter.MovieListItem
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    uiState: MainUiState,
    onRefresh: () -> Unit,
    onMovieSelected: (Movie) -> Unit,
    onFavouriteToggle: (Movie, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    PullToRefreshBox(
        isRefreshing = uiState.isFetchingMovies,
        onRefresh = onRefresh,
        modifier = modifier
            .fillMaxSize()
    ) {
        if (uiState.errorMessage.isNotEmpty()) {
            Text(
                "Error: ${uiState.errorMessage}",
                modifier = Modifier.fillMaxSize()
                    .align(Alignment.Center)
            )
        } else {
            val movies = uiState.movies.map { MovieListItem(it, it.id in uiState.favourites) }
            MoviesList(
                movies = movies,
                onMovieSelected = onMovieSelected,
                onFavouriteToggle = onFavouriteToggle,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    Random(System.currentTimeMillis())
        .nextDouble(0.0, 10.0)
    val random = Random(System.currentTimeMillis())

    val movies = (0 until 100).map {
        Movie(it, "Movie $it", "Movie overview $it", "2023-01-01", "/k42Owka8v91trK1qMYwCQCNwJKr.jpg",
            random.nextDouble(0.0, 10.0), random.nextInt(1, 1000000))
    }

    val favourites = (0 until 10).map { random.nextInt(0, 100) }

    val uiState = MainUiState(
        movies = movies,
        favourites = favourites,
        isFetchingMovies = false,
        errorMessage = ""
    )

    HomeScreen(
        uiState = uiState,
        onRefresh = {},
        onMovieSelected = {},
        onFavouriteToggle = { _, _ -> },
        modifier = Modifier
    )
}