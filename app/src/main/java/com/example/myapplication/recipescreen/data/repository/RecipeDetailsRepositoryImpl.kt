package com.example.myapplication.recipescreen.data.repository

import com.example.myapplication.recipescreen.data.datasource.remote.FirebaseRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RecipeDetailsRepositoryImpl @Inject constructor(
    private val dataSource: FirebaseRemoteDataSource
) : RecipeDetailsRepository {

    override suspend fun getRecipeSampleQuestionsFromFirebase(recipeId: String): Flow<List<String>> = flow {
        emit(dataSource.getRecipeSampleQuestionsFromFirebase(recipeId))
    }
}