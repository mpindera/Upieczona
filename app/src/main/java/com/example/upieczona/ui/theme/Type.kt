package com.example.upieczona.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.upieczona.R

val fontUpieczona = FontFamily(Font(R.font.snell_regular))

val fontContainer = FontFamily(Font(R.font.roboto_condensed_regular))


// Set of Material typography styles to start with
val Typography = Typography(
  bodyLarge = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.5.sp
  ),
  titleLarge = TextStyle(
    fontFamily = fontUpieczona,
    fontWeight = FontWeight.Normal,
    fontSize = 40.sp,
    lineHeight = 28.sp,
    letterSpacing = 0.sp
  ),
  titleMedium = TextStyle(
    fontFamily = fontContainer,
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp,
    lineHeight = 28.sp,
    letterSpacing = 0.sp
  ),
)