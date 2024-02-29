package com.example.upieczona.static_object

import androidx.core.text.HtmlCompat

object MaterialsUtils {
  fun String.decodeHtml(): String {
    return HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
  }
}