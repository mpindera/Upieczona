package com.example.upieczona.pages.content_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.upieczona.static_object.MaterialsUtils.decodeHtml
import com.example.upieczona.viewmodel.UpieczonaAPIViewModel

@Composable
fun Recipe(upieczonaAPIViewModel: UpieczonaAPIViewModel, stringPostDetails: String) {
  val fetchRecipeTitleInstruction =
    upieczonaAPIViewModel.formatInstructionsTitle(stringPostDetails)

  val up = upieczonaAPIViewModel.fetchRecipe(stringPostDetails)

  val additionalInfo = upieczonaAPIViewModel.formatInstructionsAdditionalInfo(stringPostDetails)

  var currentIndex = 0

  for ((index, recipeContent) in up.withIndex()) {
    val recipeTitleInstruction = fetchRecipeTitleInstruction.getOrNull(currentIndex)

    if (additionalInfo.isNotEmpty() && index < additionalInfo.size) {
      Text(
        modifier = Modifier.padding(8.dp),
        fontSize = 14.sp,
        textAlign = TextAlign.Start,
        text = additionalInfo[index],
        fontStyle = FontStyle.Italic
      )
    }

    if (!recipeTitleInstruction.isNullOrBlank()) {
      Divider()
      Text(
        modifier = Modifier.padding(10.dp),
        fontFamily = MaterialTheme.typography.headlineSmall.fontFamily,
        text = recipeTitleInstruction,
        fontSize = 20.sp,
        textAlign = TextAlign.Start,
      )

      currentIndex++
    }

    val decodedTextRecipeContent = recipeContent.decodeHtml()

    Column {
      Text(
        modifier = Modifier.padding(5.dp),
        fontSize = 14.sp,
        textAlign = TextAlign.Start,
        text = decodedTextRecipeContent,
      )
    }
  }
}