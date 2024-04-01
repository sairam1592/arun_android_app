package com.example.myapplication.recipescreen.data.datasource.remote

interface FirebaseRemoteDataSource {
    suspend fun getRecipeSampleQuestionsFromFirebase(recipeId: String): List<String>
}