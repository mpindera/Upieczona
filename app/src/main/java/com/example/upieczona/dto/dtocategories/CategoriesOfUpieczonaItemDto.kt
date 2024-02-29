package com.example.upieczona.dtocategories


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class CategoriesOfUpieczonaItemDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("count")
    val count: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("link")
    val link: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("taxonomy")
    val taxonomy: String,
    @SerializedName("parent")
    val parent: Int
)