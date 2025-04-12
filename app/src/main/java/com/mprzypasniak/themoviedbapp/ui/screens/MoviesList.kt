package com.mprzypasniak.themoviedbapp.ui.screens

import android.R.drawable.ic_menu_close_clear_cancel
import android.R.drawable.ic_menu_camera
import android.R.drawable.ic_menu_add
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.mprzypasniak.themoviedbapp.data.models.Movie
import com.mprzypasniak.themoviedbapp.ext.PosterSize
import com.mprzypasniak.themoviedbapp.screens.main.adapter.MovieListItem
import java.util.Locale

@Composable
fun MoviesList(
    movies: List<MovieListItem>,
    onMovieSelected: (Movie) -> Unit,
    onFavouriteToggle: (Movie, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(movies) { movie ->
            MovieListItem(
                movie = movie,
                onMovieSelected = onMovieSelected,
                onFavouriteToggle = onFavouriteToggle
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieListItem(
    movie: MovieListItem,
    onMovieSelected: (Movie) -> Unit,
    onFavouriteToggle: (Movie, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(8.dp)
            .clickable { onMovieSelected(movie.movie) }
    ) {
        GlideImage(
            model = movie.movie.getPosterUrl(PosterSize.W342),
            contentDescription = movie.movie.title,
            loading = placeholder(ic_menu_camera),
            modifier = Modifier.padding(8.dp)
        )

        Spacer(Modifier.weight(1f))

        Column {
            Text(movie.movie.title)

            Spacer(Modifier.weight(1f))

            Text(
                text = String.format(Locale.getDefault(), "%.2f", movie.movie.voteAverage),
                fontSize = 8.sp
            )
        }

        Spacer(Modifier.weight(1f))

        IconToggleButton(
            checked = movie.isFavourite,
            onCheckedChange = { onFavouriteToggle(movie.movie, it) }
        ) {
            val icon = if (movie.isFavourite) ic_menu_close_clear_cancel else ic_menu_add
            val contentDescription = if (movie.isFavourite) "Remove from favourites" else "Add to favourites"
            Image(
                painter = painterResource(id = icon),
                contentDescription = contentDescription,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Preview(showBackground = true, heightDp = 80)
@Composable
fun MovieListItemPreview() {
    val movie = Movie(
        id = 1,
        title = "Movie Title",
        overview = "Movie overview",
        posterPath = "/k42Owka8v91trK1qMYwCQCNwJKr.jpg",
        releaseDate = "2023-01-01",
        voteAverage = 8.5,
        voteCount = 100
    )

    var listItem = MovieListItem(movie, false)

    MovieListItem(
        movie = listItem,
        onMovieSelected = {},
        onFavouriteToggle = { _, toggle -> listItem = listItem.copy(isFavourite = !listItem.isFavourite) }
    )
}

@Preview(showBackground = true)
@Composable
fun MoviesListPreview() {
    val movies = listOf(
        MovieListItem(
            movie = Movie(
                id = 1,
                title = "Movie Title 1",
                overview = "Movie overview 1",
                posterPath = "/",
                releaseDate = "2023-01-01",
                voteAverage = 8.5,
                voteCount = 100
            ),
            isFavourite = false),
        MovieListItem(
            movie = Movie(
                id = 1,
                title = "Movie Title 2",
                overview = "Movie overview 2",
                posterPath = "/",
                releaseDate = "2023-01-01",
                voteAverage = 6.5,
                voteCount = 200
            ),
            isFavourite = false),
        MovieListItem(
            movie = Movie(
                id = 1,
                title = "Movie Title 3",
                overview = "Movie overview 3",
                posterPath = "/",
                releaseDate = "2023-01-01",
                voteAverage = 10.0,
                voteCount = 100
            ),
            isFavourite = true)
    )

    MoviesList(
        movies = movies,
        onMovieSelected = {},
        onFavouriteToggle = { _, _ -> }
    )
}