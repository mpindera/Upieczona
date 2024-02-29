package com.example.upieczona.dtoposts


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class OgImageDto(
    @SerializedName("url")
    val url: String,
    @SerializedName("type")
    val type: String
)