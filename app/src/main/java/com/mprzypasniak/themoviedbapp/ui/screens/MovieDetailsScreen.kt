package com.mprzypasniak.themoviedbapp.ui.screens

import android.R.drawable.btn_star_big_off
import android.R.drawable.btn_star_big_on
import android.R.drawable.ic_menu_camera
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.mprzypasniak.themoviedbapp.R
import com.mprzypasniak.themoviedbapp.data.models.Movie
import com.mprzypasniak.themoviedbapp.ext.PosterSize
import com.mprzypasniak.themoviedbapp.screens.main.MainUiState

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieDetailsScreen(
    uiState: MainUiState,
    onFavouriteToggle: (Movie, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val movie = uiState.selectedMovie

    if (movie != null) {
        Box(modifier = modifier) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                GlideImage(
                    model = movie.getPosterUrl(PosterSize.W342),
                    contentDescription = movie.title,
                    loading = placeholder(ic_menu_camera),
                    modifier = Modifier
                        .size(300.dp)
                        .padding(8.dp)
                )

                Text(
                    movie.title,
                    modifier = Modifier.padding(8.dp),
                    fontSize = TextUnit(18f, TextUnitType.Sp),
                    fontWeight = FontWeight.Bold
                )

                Text(
                    stringResource(R.string.release_date, movie.releaseDate),
                    modifier = Modifier.padding(8.dp)
                )

                Text(
                    stringResource(R.string.rating, movie.voteAverage),
                    modifier = Modifier.padding(8.dp)
                )

                Text(
                    movie.overview,
                    modifier = Modifier.weight(1f)
                )
            }

            val isFavourite = uiState.isSelectedMovieFavourite

            IconToggleButton(
                checked = isFavourite,
                onCheckedChange = { onFavouriteToggle(movie, it) },
                modifier = Modifier.align(Alignment.TopEnd).padding(8.dp)
            ) {
                val icon = if (isFavourite) btn_star_big_on else btn_star_big_off
                val contentDescription = if (isFavourite) "Remove from favourites" else "Add to favourites"
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = contentDescription,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    } else {
        Text("Error", modifier)
    }
}