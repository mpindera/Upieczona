package com.example.upieczona.dtoposts


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class PostsOfUpieczonaItemDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("author")
    val author: Int,
    @SerializedName("title")
    val title: TitleDto,
    @SerializedName("categories")
    val categories: List<Int>,
    @SerializedName("content")  //recipes & ingredients
    val content: ContentDto,
    @SerializedName("tags") // ids tags
    val tags: List<Int>,
    @SerializedName("yoast_head_json")
    val yoastHeadJson: YoastHeadJsonDto,
/*    @SerializedName("date")
    val date: String,
    @SerializedName("guid") // link with id
    val guid: GuidDto,
    @SerializedName("link") // link to site with slug
    val link: String*/
)