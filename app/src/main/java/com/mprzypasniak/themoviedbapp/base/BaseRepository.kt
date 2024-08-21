package com.mprzypasniak.themoviedbapp.base

import com.google.gson.Gson
import com.mprzypasniak.themoviedbapp.data.models.responses.StatusResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import retrofit2.Response

abstract class BaseRepository {
    protected fun <T> result(block: () -> T): Result<T> {
        return runBlocking(Dispatchers.IO) {
            try {
                Result.success(block.invoke())
            } catch (e: Throwable) {
                Result.failure(e)
            }
        }
    }

    protected fun <T> resolveNetworkResponse(block: suspend () -> Response<T>): T & Any {
        return runBlocking(Dispatchers.IO) {
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