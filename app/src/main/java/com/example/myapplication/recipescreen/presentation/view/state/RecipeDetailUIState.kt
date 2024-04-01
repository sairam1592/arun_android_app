package com.example.myapplication.recipescreen.presentation.view.state

import com.example.myapplication.arunproject.data.model.Recipe

data class RecipeDetailUIState(
    var recipeDetail: Recipe? = null,
    var recipeSampleQuestions: List<String> = emptyList()
)