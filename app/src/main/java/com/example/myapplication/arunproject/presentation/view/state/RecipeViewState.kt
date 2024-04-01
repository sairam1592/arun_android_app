package com.example.myapplication.arunproject.presentation.view.state

import com.example.myapplication.arunproject.data.model.Recipe

data class RecipeViewState(
    val isLoading: Boolean = false,
    val recipes: List<Recipe> = emptyList(),
    val selectedRecipeId: String? = null,
    val isShowGrid: Boolean = false,
    val searchQuery: String = "",
    val cart: List<String> = emptyList(),
    val isAddToCartButtonEnabled: Boolean = false,
    val isViewInfoButtonEnabled : Boolean = false,
    val errorMessage: String = ""
)