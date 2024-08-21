package com.mprzypasniak.themoviedbapp.di

import androidx.room.Room
import com.mprzypasniak.themoviedbapp.data.database.FavouritesDatabase
import com.mprzypasniak.themoviedbapp.data.repositories.AuthenticationRepository
import com.mprzypasniak.themoviedbapp.data.repositories.MoviesRepository
import com.mprzypasniak.themoviedbapp.data.repositories.implementations.AuthenticationRepositoryImpl
import com.mprzypasniak.themoviedbapp.data.repositories.implementations.MoviesRepositoryImpl
import com.mprzypasniak.themoviedbapp.network.api.AuthApi
import com.mprzypasniak.themoviedbapp.network.api.MoviesApi
import com.mprzypasniak.themoviedbapp.network.interceptors.AuthInterceptor
import com.mprzypasniak.themoviedbapp.screens.main.MainViewModel
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    viewModel { MainViewModel(get(), get()) }
}

val dataModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            FavouritesDatabase::class.java, "favourites-database"
        ).build()
    }

    factory<AuthenticationRepository> {
        AuthenticationRepositoryImpl(get())
    }

    factory<MoviesRepository> {
        MoviesRepositoryImpl(get(), get())
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    factory { get<Retrofit>().create(AuthApi::class.java) }
    factory { get<Retrofit>().create(MoviesApi::class.java) }
}