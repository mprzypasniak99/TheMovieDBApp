package com.mprzypasniak.themoviedbapp.network.api

import com.mprzypasniak.themoviedbapp.data.models.StatusResponse
import com.mprzypasniak.themoviedbapp.network.ApiConstants
import retrofit2.Call
import retrofit2.http.GET

interface AuthApi {
    @GET(ApiConstants.AUTHENTICATE_TOKEN)
    fun authenticateTokenAsync(): Call<StatusResponse>
}