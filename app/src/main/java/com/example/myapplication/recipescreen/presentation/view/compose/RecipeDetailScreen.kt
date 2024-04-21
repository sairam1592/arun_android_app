package com.example.myapplication.recipescreen.presentation.view.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.arun.myrecipeapplication.R
import com.example.myapplication.arunproject.common.AppConstants
import com.example.myapplication.recipescreen.presentation.view.state.RecipeDetailUIState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(
    recipeInfo: RecipeDetailUIState,
    onChooseQuestion: (String) -> Unit,
    onUpClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = AppConstants.RECIPE_DETAIL_TITLE)
                },
                colors =
                    TopAppBarColors(
                        containerColor = colorResource(id = R.color.blue_primary),
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White,
                        actionIconContentColor = Color.White,
                        scrolledContainerColor = Color.White,
                    ),
                navigationIcon = {
                    IconButton(onClick = { onUpClick() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        },
    ) { innerPadding ->
        Column(
            modifier =
            Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            Image(
                painter = rememberAsyncImagePainter(recipeInfo.recipeDetail?.image),
                contentDescription = recipeInfo.recipeDetail?.description,
                modifier =
                Modifier
                    .aspectRatio(16f / 9f)
                    .height(278.dp),
                contentScale = ContentScale.Crop,
            )

            Column(
                modifier =
                    Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(10.dp),
            ) {
                Text(
                    text = recipeInfo.recipeDetail?.name.toString(),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                )

                Text(
                    text = recipeInfo.recipeDetail?.headline ?: "",
                    style = MaterialTheme.typography.headlineSmall,
                    color = colorResource(id = R.color.blue_primary),
                    modifier = Modifier.padding(vertical = 4.dp),
                )

                Text(
                    text = recipeInfo.recipeDetail?.description.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                )

                DropdownQuestionsMenu(
                    modifier =
                        Modifier.padding(
                            horizontal = 10.dp,
                            vertical = 3.dp,
                        ),
                    questions = recipeInfo.recipeSampleQuestions,
                    onChooseQuestion = onChooseQuestion,
                )
            }
        }
    }
}

@Composable
fun DropdownQuestionsMenu(
    modifier: Modifier,
    questions: List<String>,
    onChooseQuestion: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier =
            Modifier.run {
                padding(horizontal = 16.dp)
                    .fillMaxWidth()
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "Got a doubt? Click here to ask!",
            style = MaterialTheme.typography.titleMedium,
        )

        val iconButton = @Composable {
            IconButton(onClick = { expanded = true }) {
                Icon(Icons.Filled.Add, contentDescription = "Localized description")
            }
        }

        val menuShape = RoundedCornerShape(8.dp)
        val borderColor = colorResource(id = R.color.blue_primary)
        val borderStroke = BorderStroke(1.dp, borderColor)

        Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
            iconButton()

            // Create a dropdown menu with expanded state and list of questions
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier =
                    Modifier
                        .border(borderStroke, menuShape)
                        .background(MaterialTheme.colorScheme.surface, menuShape),
            ) {
                questions.forEachIndexed { index, question ->
                    DropdownMenuItem(
                        onClick = {
                            onChooseQuestion(question)
                            expanded = false
                        },
                    ) {
                        Text(
                            text = question,
                            style = MaterialTheme.typography.bodyMedium,
                            color = colorResource(id = R.color.blue_primary),
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DropdownMenuSamplePreview() {
    val questions =
        listOf(
            "How many calories are in this dish?",
            "What are the ingredients?",
            "How long will it take to prepare?",
        )

    DropdownQuestionsMenu(modifier = Modifier, questions, onChooseQuestion = {})
}

@Preview(showBackground = true)
@Composable
fun PreviewRecipeDetailScreen() {
    RecipeDetailScreen(onUpClick = { }, recipeInfo = RecipeDetailUIState(), onChooseQuestion = {})
}
