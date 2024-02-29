package com.example.upieczona.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.upieczona.data.domain.repository.Repository
import com.example.upieczona.dtocategories.CategoriesOfUpieczonaItemDto
import com.example.upieczona.dtoposts.PostsOfUpieczonaItemDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpieczonaAPIViewModel @Inject constructor(
  val repository: Repository
) : ViewModel() {

  private val _select = MutableStateFlow(0)
  val select: StateFlow<Int> = _select

  private val _categoriesState = MutableStateFlow<List<CategoriesOfUpieczonaItemDto>>(emptyList())
  val categoriesState: StateFlow<List<CategoriesOfUpieczonaItemDto>> = _categoriesState

  private val _stateNames = MutableStateFlow<List<PostsOfUpieczonaItemDto>>(emptyList())
  val stateNames: StateFlow<List<PostsOfUpieczonaItemDto>> = _stateNames

  private val _allPosts = MutableStateFlow<List<PostsOfUpieczonaItemDto>>(emptyList())
  val allPosts: StateFlow<List<PostsOfUpieczonaItemDto>> = _allPosts

  private val _allCategoryPosts = MutableStateFlow<List<PostsOfUpieczonaItemDto>>(emptyList())
  val allCategoryPosts: StateFlow<List<PostsOfUpieczonaItemDto>> = _allCategoryPosts

  private val _error = MutableStateFlow<String?>(null)
  val error: StateFlow<String?> = _error

  private val _isLoading = MutableStateFlow(false)
  val isLoading: StateFlow<Boolean> = _isLoading


  init {
    fetchCategoriesAndFirstPage()
  }

  fun fetchCategoriesAndFirstPage() {
    viewModelScope.launch(Dispatchers.IO) {
      try {
        _isLoading.value = true

        val categories = repository.fetchAllCategories()
        _categoriesState.value = categories

        val posts = repository.fetchAllPostsFromFirstPage()
        _allPosts.value = posts

      } catch (e: Exception) {
        Log.e("UpieczonaViewModel", "Error fetching data: ${e.message}")
      } finally {
        _isLoading.value = false
      }
    }
  }

  suspend fun fetchAllPosts() {
    try {
      _allPosts.value = emptyList()
      coroutineScope {
        var page = 1
        while (true) {
          val posts = repository.fetchPostsFromFirstPage(page)

          if (posts.isNotEmpty()) {
            _allPosts.value = _allPosts.value + posts
            page++
          } else {
            break
          }
        }
      }
    } catch (e: Exception) {
      _error.value = "Error fetching post details"
      Log.e("UpieczonaViewModel", error.value.toString())
    }
  }

  suspend fun fetchAllPostsByCategory(categoryId: Int) {
    try {
      _allCategoryPosts.value = emptyList()
      var page = 1
      var posts: List<PostsOfUpieczonaItemDto>
      do {
        posts = repository.fetchAllCategoryPosts(categoryId, page)
        _allCategoryPosts.value = _allCategoryPosts.value + posts
        page++
      } while (posts.isNotEmpty())
    } catch (e: Exception) {
      _error.value = "Error fetching post details"
      Log.e("UpieczonaViewModel", error.value.toString())
    }
  }

}
