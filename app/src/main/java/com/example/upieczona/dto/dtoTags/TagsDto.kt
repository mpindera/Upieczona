package com.example.upieczona.dtoTags


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class TagsDto(
    @SerializedName("count")
    val count: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("link")
    val link: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("slug")
    val slug: String
)