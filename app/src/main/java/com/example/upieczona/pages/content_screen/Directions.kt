package com.example.upieczona.pages.content_screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun Directions() {
  LazyColumn(content = {
    items(10) {
      Text(text = "")
    }
  })
}