package com.example.upieczona.static_object

import androidx.core.text.HtmlCompat

object MaterialsUtils {
  fun String.decodeHtml(): String {
    return HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
  }

  val regexPatternPhotosUpieczona =
    """http://www\.upieczona\.pl/wp-content/uploads/\d{4}/\d{2}/[^"]+\.(jpg|jpeg|png)""".toRegex()

  val regexPatternTitleIngredientsUpieczona =
    """<h3 class="ingredients-title">(.*)</h3>""".toRegex()

  val regexPatternShopListUpieczona =
    """<p class="ingredient-item-name is-strikethrough-active">(.*?)</p>"""

  val ingredientsListPattern =
    Regex("<ul class=\"ingredients-list\">(.*?)</ul>", RegexOption.DOT_MATCHES_ALL)

  val recipeTitleInstruction = """<div class="entry-content"><p>(.*?)</p></div>""".toRegex()
}