package com.mprzypasniak.themoviedbapp.network.interceptors

import com.mprzypasniak.themoviedbapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val authenticatedRequest = request.newBuilder()
            .header("Authorization", "Bearer ${BuildConfig.TOKEN}")
            .build()

        return chain.proceed(authenticatedRequest)
    }
}