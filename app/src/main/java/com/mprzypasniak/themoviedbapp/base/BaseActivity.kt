package com.mprzypasniak.themoviedbapp.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import com.mprzypasniak.themoviedbapp.ui.theme.TheMovieDbAppTheme

abstract class BaseActivity<VM: ViewModel>: ComponentActivity() {
    protected abstract val vm: VM
    protected abstract val view: @Composable () -> Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TheMovieDbAppTheme {
                view()
            }
        }
    }
}