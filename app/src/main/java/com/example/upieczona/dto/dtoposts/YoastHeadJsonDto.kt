package com.example.upieczona.dtoposts


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class YoastHeadJsonDto(
    @SerializedName("og_title")
    val ogTitle: String,
    @SerializedName("og_description")
    val ogDescription: String,
    @SerializedName("twitter_image")
    val twitterImage: String,
    @SerializedName("twitter_misc")
    val twitterMisc: TwitterMiscDto,
    @SerializedName("og_image")
    val ogImage: List<OgImageDto>,

    )