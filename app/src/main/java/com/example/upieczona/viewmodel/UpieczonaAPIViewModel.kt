package com.example.upieczona.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.upieczona.data.domain.repository.Repository
import com.example.upieczona.dtocategories.CategoriesOfUpieczonaItemDto
import com.example.upieczona.dtoposts.PostsOfUpieczonaItemDto
import com.example.upieczona.static_object.MaterialsUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import javax.inject.Inject

@HiltViewModel
class UpieczonaAPIViewModel @Inject constructor(
  private val repository: Repository
) : ViewModel() {

  private val _select = MutableStateFlow(0)
  val select: StateFlow<Int> = _select

  val categoriesState = MutableStateFlow(emptyList<CategoriesOfUpieczonaItemDto>())

  val stateNames = MutableStateFlow(emptyList<PostsOfUpieczonaItemDto>())

  private val _allPosts = mutableStateOf<List<PostsOfUpieczonaItemDto>>(emptyList())
  val allPosts: State<List<PostsOfUpieczonaItemDto>> = _allPosts

  private val _allCategoryPosts = mutableStateOf<List<PostsOfUpieczonaItemDto>>(emptyList())
  val allCategoryPosts: State<List<PostsOfUpieczonaItemDto>> = _allCategoryPosts


  private val _error = MutableStateFlow<String?>(null)
  val error: StateFlow<String?> = _error

  private val _isLoading = MutableStateFlow(false)
  val isLoading: StateFlow<Boolean> = _isLoading


  init {
    fetchCategoriesAndFirstPage()
  }

  private fun fetchCategoriesAndFirstPage() {
    viewModelScope.launch(Dispatchers.IO) {
      try {
        _isLoading.value = true

        stateNames.value = repository.fetchAllPostsFromFirstPage()
        categoriesState.value = repository.fetchAllCategories()

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

  fun extractPhotosUrls(content: String): List<String> {
    return MaterialsUtils.regexPatternPhotosUpieczona.findAll(content).map { it.value }.toList()
      .distinct()
  }



  private val ingredientCache = mutableMapOf<String, List<String>>()
  fun formatInstructionsTitle(input: String): List<String> {
    val regex = """<strong>(.*)<\/strong>""".toRegex()
    return regex.findAll(input).map { it.groupValues[1] }.toList()
  }

  fun getCachedIngredients(content: String): List<String> {
    if (ingredientCache.containsKey(content)) {
      return ingredientCache[content]!!
    }

    val ingredients = extractIngredients(content)
    ingredientCache[content] = ingredients
    return ingredients
  }

  fun extractIngredients(content: String): List<String> {
    val matches = MaterialsUtils.regexPatternTitleIngredientsUpieczona.findAll(content)
    return matches.map { it.groupValues[1] }.toList()
  }

  fun ingredientsLists(content: String): List<MatchResult> {
    return MaterialsUtils.ingredientsListPattern.findAll(content).toList()
  }
  fun formatInstructionsAdditionalInfo(input: String): List<String> {
    val document = Jsoup.parse(input)
    return document.select("p:has(em)").map {
      it.toString().replace("<p>", "").replace("<em>", "").replace("</p>", "")
        .replace("</em>", "\n")
    }.toList()
  }
  fun fetchRecipe(input: String): List<String> {
    val pattern = "<p>.*?</p>".toRegex()
    val matches = pattern.findAll(input).map { it.value }.toList()

    val document = Jsoup.parse(matches.toString())

    val paragraphs = document.select("p:not(:has(em))").map { paragraph ->
      var paragraphHtml = paragraph.html()
      paragraphHtml = paragraphHtml.replaceFirst("<br>", "   ")
      paragraphHtml = paragraphHtml.replace(Regex("<strong>.*?</strong>"), "")
      paragraphHtml = paragraphHtml.replace("<p>", "\n")
      paragraphHtml = paragraphHtml.replace("</p>", "\n")

      paragraphHtml
    }

    return paragraphs
  }
}
