package com.example.myapplication.recipescreen.data.repository

import kotlinx.coroutines.flow.Flow

interface RecipeDetailsRepository {
    suspend fun getRecipeSampleQuestionsFromFirebase(recipeId: String): Flow<List<String>>
}