package com.bewusstlos.moviessampleapp.data.model

import com.google.gson.annotations.SerializedName

data class GenericResponse(
    @SerializedName("status_message")
    val message: String,
    @SerializedName("status_code")
    val code: String
)