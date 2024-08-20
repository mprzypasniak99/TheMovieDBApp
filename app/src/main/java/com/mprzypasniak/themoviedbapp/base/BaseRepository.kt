package com.mprzypasniak.themoviedbapp.base

import com.google.gson.Gson
import com.mprzypasniak.themoviedbapp.data.models.StatusResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import retrofit2.Response

abstract class BaseRepository {
    fun <T> result(block: suspend () -> Response<T>): Result<T> {
        return runBlocking(Dispatchers.IO) {
            try {
                val response = block.invoke()

                if (response.isSuccessful) {
                    Result.success(response.body() ?: throw Throwable("Unable to parse response"))
                } else {
                    val gson = Gson()
                    val errorBody = gson.fromJson(
                        response.errorBody()?.charStream(),
                        StatusResponse::class.java
                    )
                    throw Throwable(errorBody.statusMessage)
                }
            } catch (e: Throwable) {
                Result.failure(e)
            }
        }
    }
}