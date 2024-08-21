package com.mprzypasniak.themoviedbapp.ext

import android.widget.ImageView
import com.bumptech.glide.Glide

enum class PosterSize(val symbol: String) {
    W92("w92"),
    W154("w154"),
    W185("w185"),
    W342("w342"),
    W500("w500"),
    W780("w780"),
    ORIGINAL("original")
}

fun ImageView.loadPoster(path: String, size: PosterSize) {
    val url = StringBuilder("https://image.tmdb.org/t/p/")
        .append(size.symbol)
        .append(path)
        .toString()

    Glide.with(this)
        .load(url)
        .into(this)
}