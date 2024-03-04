package com.example.upieczona.pages.content_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.upieczona.dtoposts.PostsOfUpieczonaItemDto
import com.example.upieczona.static_object.MaterialsUtils
import com.example.upieczona.viewmodel.UpieczonaAPIViewModel


@Composable
fun Ingredients(
  upieczonaAPIViewModel: UpieczonaAPIViewModel,
  postDetails: PostsOfUpieczonaItemDto?,
  ingredientTitleUpieczona: List<String>,
) {
  for (i in ingredientTitleUpieczona.indices) {
    val firstIngredients = Regex(MaterialsUtils.regexPatternShopListUpieczona).findAll(
      upieczonaAPIViewModel.ingredientsLists(postDetails?.content?.rendered.toString())[i].groupValues[1]
    ).map { it.groupValues[1] }.toList()
    val ingredientStates = remember {
      firstIngredients.map { title ->
        mutableStateOf(false)
      }
    }
    Spacer(modifier = Modifier.height(20.dp))
    Text(
      modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp),
      fontFamily = MaterialTheme.typography.headlineSmall.fontFamily,
      text = ingredientTitleUpieczona[i].uppercase(),
      textAlign = TextAlign.Start,
      fontSize = 18.sp,
      fontWeight = FontWeight.Bold,
    )
    if (upieczonaAPIViewModel.ingredientsLists(postDetails?.content?.rendered.toString())
        .isNotEmpty()
    ) {

      ingredientStates.forEachIndexed { index, isCheckedState ->
        Box(modifier = Modifier.clickable {
          isCheckedState.value = !isCheckedState.value
        }) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = isCheckedState.value, onCheckedChange = {
              isCheckedState.value = it
            })

            Text(
              modifier = Modifier.padding(start = 8.dp, end = 8.dp),
              fontFamily = MaterialTheme.typography.headlineSmall.fontFamily,
              text = firstIngredients[index],
              fontSize = 14.sp,
              textAlign = TextAlign.Start,
              textDecoration = if (isCheckedState.value) TextDecoration.LineThrough else TextDecoration.None
            )
          }
          Divider()
        }
      }
    }
  }
}

@Composable
@Preview(showBackground = true)
fun test() {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(top = 10.dp)
  ) {
    Spacer(modifier = Modifier.height(15.dp))
    Text(
      modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp),
      fontFamily = MaterialTheme.typography.headlineSmall.fontFamily,
      text = "ingredientTitle()",
      textAlign = TextAlign.Start,
      fontSize = 18.sp,
      fontWeight = FontWeight.Bold,
    )
    for (i in 1..5) {
      Box(modifier = Modifier.clickable {

      }) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(
            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
            fontFamily = MaterialTheme.typography.headlineSmall.fontFamily,
            text = "firstIngredients[index]",
            fontSize = 14.sp,
            textAlign = TextAlign.Start,
          )
        }
        Divider()
      }
    }
  }
}