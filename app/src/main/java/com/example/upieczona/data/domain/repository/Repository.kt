package com.example.upieczona.data.domain.repository

import com.example.upieczona.dtoTags.TagsDto
import com.example.upieczona.dtocategories.CategoriesOfUpieczona
import com.example.upieczona.dtoposts.PostsOfUpieczona
import retrofit2.http.GET
import retrofit2.http.Query

interface Repository {
  suspend fun fetchAllPostsFromFirstPage(): PostsOfUpieczona

  // Fetch all category from website
  suspend fun fetchAllCategories(): CategoriesOfUpieczona

  // Fetch all posts from main page
  suspend fun fetchPostsFromFirstPage(@Query("page") categoryId: Int): PostsOfUpieczona

  // Fetch all posts from different category pages
  suspend fun fetchAllCategoryPosts(
    @Query("categories") categoryId: Int, @Query("page") page: Int

  ): PostsOfUpieczona
}