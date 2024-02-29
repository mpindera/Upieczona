package com.example.upieczona.data.remote.repository

import android.app.Application
import com.example.upieczona.R
import com.example.upieczona.data.domain.repository.Repository
import com.example.upieczona.data.remote.ApiService
import com.example.upieczona.dtoTags.TagsDto
import com.example.upieczona.dtocategories.CategoriesOfUpieczona
import com.example.upieczona.dtoposts.PostsOfUpieczona

class RepositoryImpl(
  private val api: ApiService,
) : Repository {

  override suspend fun fetchAllPostsFromFirstPage(): PostsOfUpieczona {
    return api.fetchAllPostsFromFirstPage()
  }

  override suspend fun fetchAllCategories(): CategoriesOfUpieczona {
    return api.fetchAllCategories()
  }

  override suspend fun fetchPostsFromFirstPage(categoryId: Int): PostsOfUpieczona {
    return api.fetchPostsFromFirstPage(categoryId)
  }

  override suspend fun fetchAllCategoryPosts(categoryId: Int, page: Int): PostsOfUpieczona {
    return api.fetchAllCategoryPosts(categoryId, page)
  }
}