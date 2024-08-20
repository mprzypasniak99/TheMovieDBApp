package com.mprzypasniak.themoviedbapp.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.mprzypasniak.themoviedbapp.R

abstract class BaseActivity<VM: ViewModel, T: ViewBinding>: AppCompatActivity() {
    protected lateinit var binding: T
    protected abstract val vm: VM
    abstract val inflater: (LayoutInflater) -> T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflater(layoutInflater)

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}