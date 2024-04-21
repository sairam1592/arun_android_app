package com.example.myapplication.recipescreen.presentation.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myapplication.recipescreen.presentation.view.compose.RecipeDetailScreen
import com.example.myapplication.recipescreen.presentation.viewmodel.RecipeDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeDetailsActivity : AppCompatActivity() {
    private val viewModel: RecipeDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val recipeId = intent.getStringExtra(EXTRA_RECIPE_ID)

        recipeId?.let {
            viewModel.getRecipeById(it)
            viewModel.getRecipeSampleQuestionsFromFirebase(it)
        }

        setContent {
            val recipeInfo by viewModel.recipeDetailState.collectAsStateWithLifecycle()
            RecipeDetailScreen(
                recipeInfo = recipeInfo,
                onUpClick = { finish() },
                onChooseQuestion = { question ->
                    // TODO Handle question selection, call GEMINI API
                },
            )
        }
    }

    companion object {
        const val EXTRA_RECIPE_ID = "extra_recipe_id"

        // TODO Remove this usage and use Compose Navigator
        fun startActivity(
            context: Context,
            recipeId: String,
        ) {
            val intent =
                Intent(context, RecipeDetailsActivity::class.java).apply {
                    putExtra(EXTRA_RECIPE_ID, recipeId)
                }
            context.startActivity(intent)
        }
    }
}
