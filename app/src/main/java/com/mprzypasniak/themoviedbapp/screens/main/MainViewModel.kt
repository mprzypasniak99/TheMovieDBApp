package com.mprzypasniak.themoviedbapp.screens.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mprzypasniak.themoviedbapp.data.repositories.AuthenticationRepository

class MainViewModel(
    private val authRepo: AuthenticationRepository
): ViewModel() {
    val _errorMessage = MutableLiveData("")
    val errorMessage: LiveData<String>
        get() = _errorMessage

    fun authenticateToken() {
        authRepo.authenticateToken()
            .onSuccess {
                _errorMessage.postValue(it.statusMessage)
            }
            .onFailure {
                _errorMessage.postValue(it.message)
            }
    }
}