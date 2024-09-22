package com.mprzypasniak.themoviedbapp.base

import com.google.gson.Gson
import com.mprzypasniak.themoviedbapp.data.models.responses.StatusResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

abstract class BaseRepository {
    protected suspend fun <T> result(block: suspend () -> T): Result<T> {
        return withContext(Dispatchers.IO) {
            try {
                Result.success(block.invoke())
            } catch (e: Throwable) {
                Result.failure(e)
            }
        }
    }

    protected suspend fun <T> resolveNetworkResponse(block: suspend () -> Response<T>): T & Any {
        return withContext(Dispatchers.IO) {
            val response = block.invoke()

            if (response.isSuccessful) {
                response.body() ?: throw Throwable("Unable to parse response")
            } else {
                val gson = Gson()
                val errorBody = gson.fromJson(
                    response.errorBody()?.charStream(),
                    StatusResponse::class.java
                )
                throw Throwable(errorBody.statusMessage)
            }
        }
    }
}