package com.example.myapplication.recipescreen.presentation.view.compose

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.arun.myrecipeapplication.R
import com.example.myapplication.arunproject.common.AppConstants
import com.example.myapplication.recipescreen.presentation.viewmodel.RecipeDetailsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(viewModel: RecipeDetailsViewModel = viewModel(), onUpClick: () -> Unit) {
    val recipeInfo by viewModel.recipeDetailState.collectAsStateWithLifecycle()
    Log.d("testres", "recipeInfo: ${recipeInfo.recipeSampleQuestions}")
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = AppConstants.RECIPE_DETAIL_TITLE)
                },
                colors = TopAppBarColors(
                    containerColor = colorResource(id = R.color.blue_primary),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White,
                    scrolledContainerColor = Color.White
                ),
                navigationIcon = {
                    IconButton(onClick = { onUpClick() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }

            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            Image(
                painter = rememberAsyncImagePainter(recipeInfo.recipeDetail?.image),
                contentDescription = recipeInfo.recipeDetail?.description,
                modifier = Modifier
                    .aspectRatio(16f / 9f)
                    .height(278.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                Text(
                    text = recipeInfo.recipeDetail?.name.toString(),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = recipeInfo.recipeDetail?.headline ?: "",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Text(
                    text = recipeInfo.recipeDetail?.description.toString(),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewRecipeDetailScreen() {
    RecipeDetailScreen(onUpClick = { })
}