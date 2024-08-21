package com.mprzypasniak.themoviedbapp.data.repositories

import com.mprzypasniak.themoviedbapp.data.models.responses.StatusResponse

interface AuthenticationRepository {
    fun authenticateToken(): Result<StatusResponse>
}