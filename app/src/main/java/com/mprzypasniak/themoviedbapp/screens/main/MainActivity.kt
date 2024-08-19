package com.mprzypasniak.themoviedbapp.screens.main

import android.os.Bundle
import android.view.LayoutInflater
import com.mprzypasniak.themoviedbapp.base.BaseActivity
import com.mprzypasniak.themoviedbapp.databinding.ActivityMainBinding

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(MainViewModel::class) {
    override val inflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}