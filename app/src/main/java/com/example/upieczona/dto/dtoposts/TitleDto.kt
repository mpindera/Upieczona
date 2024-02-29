package com.example.upieczona.dtoposts


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class TitleDto(
    @SerializedName("rendered")
    val rendered: String
)