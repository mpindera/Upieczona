package com.example.upieczona.dtoposts


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class TwitterMiscDto(
    @SerializedName("Napisane przez")
    val napisanePrzez: String,
    @SerializedName("Szacowany czas czytania")
    val szacowanyCzasCzytania: String
)