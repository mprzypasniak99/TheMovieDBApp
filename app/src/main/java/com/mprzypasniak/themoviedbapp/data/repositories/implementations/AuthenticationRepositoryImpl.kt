package com.mprzypasniak.themoviedbapp.data.repositories.implementations

import com.mprzypasniak.themoviedbapp.base.BaseRepository
import com.mprzypasniak.themoviedbapp.data.repositories.AuthenticationRepository
import com.mprzypasniak.themoviedbapp.network.api.AuthApi

class AuthenticationRepositoryImpl(private val api: AuthApi): BaseRepository(), AuthenticationRepository {
    override fun authenticateToken() = result {
        resolveNetworkResponse {
            api.authenticateToken().execute()
        }
    }
}