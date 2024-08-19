package com.mprzypasniak.themoviedbapp.di

import com.mprzypasniak.themoviedbapp.screens.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { MainViewModel() }
}

val dataModule = module {

}