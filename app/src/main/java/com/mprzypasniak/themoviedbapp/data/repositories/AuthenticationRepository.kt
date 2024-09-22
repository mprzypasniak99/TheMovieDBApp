package com.mprzypasniak.themoviedbapp.data.repositories

import com.mprzypasniak.themoviedbapp.data.models.responses.StatusResponse

interface AuthenticationRepository {
    suspend fun authenticateToken(): Result<StatusResponse>
}