package com.mprzypasniak.themoviedbapp.data.repositories

import com.mprzypasniak.themoviedbapp.data.models.StatusResponse

interface AuthenticationRepository {
    fun authenticateToken(): Result<StatusResponse>
}