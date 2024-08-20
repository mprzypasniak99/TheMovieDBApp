package com.mprzypasniak.themoviedbapp.screens.main

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.mprzypasniak.themoviedbapp.base.BaseActivity
import com.mprzypasniak.themoviedbapp.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {
    override val inflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate
    override val vm: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm.errorMessage.observe(this) {
            if (it.isNotBlank())
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }

        vm.authenticateToken()
    }
}