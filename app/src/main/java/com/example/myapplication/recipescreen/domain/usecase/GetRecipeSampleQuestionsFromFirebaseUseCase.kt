package com.example.myapplication.recipescreen.domain.usecase

import com.example.myapplication.recipescreen.data.repository.RecipeDetailsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecipeSampleQuestionsFromFirebaseUseCase @Inject constructor(
    private val repository: RecipeDetailsRepository
) {
    suspend operator fun invoke(recipeId: String): Flow<List<String>> {
        return repository.getRecipeSampleQuestionsFromFirebase(recipeId)
    }
}