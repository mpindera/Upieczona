package com.example.upieczona.data.remote

import com.example.upieczona.dtoTags.TagsDto
import com.example.upieczona.dtocategories.CategoriesOfUpieczona
import com.example.upieczona.dtoposts.PostsOfUpieczona
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
  // Fetch 10 posts from main page
  @GET("wp-json/wp/v2/posts")
  suspend fun fetchAllPostsFromFirstPage(): PostsOfUpieczona

  // Fetch all category from website
  @GET("wp-json/wp/v2/categories")
  suspend fun fetchAllCategories(): CategoriesOfUpieczona

  // Fetch all posts from main page
  @GET("wp-json/wp/v2/posts")
  suspend fun fetchPostsFromFirstPage(@Query("page") categoryId: Int): PostsOfUpieczona

  // Fetch all posts from different category pages
  @GET("wp-json/wp/v2/posts")
  suspend fun fetchAllCategoryPosts(
    @Query("categories") categoryId: Int,
    @Query("page") page: Int

  ): PostsOfUpieczona
}