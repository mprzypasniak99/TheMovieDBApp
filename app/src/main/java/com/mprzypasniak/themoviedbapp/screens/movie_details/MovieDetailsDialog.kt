package com.mprzypasniak.themoviedbapp.screens.movie_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mprzypasniak.themoviedbapp.R
import com.mprzypasniak.themoviedbapp.databinding.DialogMovieDetailsBinding
import com.mprzypasniak.themoviedbapp.ext.PosterSize
import com.mprzypasniak.themoviedbapp.ext.loadPoster
import com.mprzypasniak.themoviedbapp.screens.main.MainViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class MovieDetailsDialog : BottomSheetDialogFragment() {
    companion object {
        const val  TAG = "MovieDetails"
    }

    private lateinit var binding: DialogMovieDetailsBinding
    private val vm: MainViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.uiState.collect {
                    val selectedMovie = it.selectedMovie
                    if (selectedMovie == null) {
                        dismiss()
                        return@collect
                    }

                    val isFavourite = selectedMovie.id in it.favourites
                    with(binding) {
                        tvDetailsTitle.text = selectedMovie.title
                        tvDetailsReleaseDate.text = getString(R.string.release_date, selectedMovie.releaseDate)
                        tvDetailsRating.text = getString(R.string.rating, selectedMovie.voteAverage)
                        tvDetailsDescription.text = selectedMovie.overview
                        cbDetailsFavourite.isChecked = selectedMovie.id in it.favourites

                        cbDetailsFavourite.setOnClickListener { _ ->
                            vm.toggleFavouriteOnMovie(selectedMovie, !isFavourite)
                        }

                        ivPoster.loadPoster(selectedMovie.posterPath, PosterSize.W780)
                    }
                }
            }
        }
    }
}