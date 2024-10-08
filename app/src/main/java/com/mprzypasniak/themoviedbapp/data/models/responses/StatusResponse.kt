package com.mprzypasniak.themoviedbapp.data.models.responses

import com.google.gson.annotations.SerializedName

data class StatusResponse(
    val success: Boolean,
    @SerializedName("status_code") val statusCode: Int,
    @SerializedName("status_message") val statusMessage: String
)
