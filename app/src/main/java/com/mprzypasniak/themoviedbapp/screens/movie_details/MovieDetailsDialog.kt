package com.mprzypasniak.themoviedbapp.screens.movie_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mprzypasniak.themoviedbapp.R
import com.mprzypasniak.themoviedbapp.databinding.DialogMovieDetailsBinding
import com.mprzypasniak.themoviedbapp.ext.PosterSize
import com.mprzypasniak.themoviedbapp.ext.loadPoster
import com.mprzypasniak.themoviedbapp.screens.main.MainViewModel
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

        vm.selectedMovie.observe(this) {
            if (it == null) {
                dismiss()
                return@observe
            }
            with(binding) {
                tvDetailsTitle.text = it.title
                tvDetailsReleaseDate.text = getString(R.string.release_date, it.releaseDate)
                tvDetailsRating.text = getString(R.string.rating, it.voteAverage)
                tvDetailsDescription.text = it.overview
                cbDetailsFavourite.isChecked = it.isFavourite

                cbDetailsFavourite.setOnClickListener { _ ->
                    vm.toggleFavouriteOnMovie(it, !it.isFavourite)
                }

                ivPoster.loadPoster(it.posterPath, PosterSize.W780)
            }
        }
    }
}