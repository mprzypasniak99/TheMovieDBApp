package com.mprzypasniak.themoviedbapp

import android.app.Application
import com.mprzypasniak.themoviedbapp.di.appModule
import com.mprzypasniak.themoviedbapp.di.dataModule
import com.mprzypasniak.themoviedbapp.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TheMovieDBApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(applicationContext)
            modules(appModule, dataModule, networkModule)
        }
    }
}