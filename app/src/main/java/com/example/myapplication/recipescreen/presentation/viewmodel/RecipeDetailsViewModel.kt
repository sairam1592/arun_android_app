package com.example.myapplication.recipescreen.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.arunproject.domain.usecase.GetRecipeByIdUseCase
import com.example.myapplication.recipescreen.domain.usecase.GetRecipeSampleQuestionsFromFirebaseUseCase
import com.example.myapplication.recipescreen.presentation.view.state.RecipeDetailUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    private val getRecipeByIdUseCase: GetRecipeByIdUseCase,
    private val getRecipeSampleQuestionsFromFirebaseUseCase: GetRecipeSampleQuestionsFromFirebaseUseCase
) : ViewModel() {

    private val _recipeDetailState = MutableStateFlow(RecipeDetailUIState())
    val recipeDetailState = _recipeDetailState.asStateFlow()

    //TODO HANDLE ERROR CASE
    //TODO FETCH normal details from DB AND SHOW IN COMPOSE SCREEN if any error
    fun getRecipeSampleQuestionsFromFirebase(recipeId: String) {
        viewModelScope.launch {
            getRecipeSampleQuestionsFromFirebaseUseCase(recipeId).collect { result ->
                _recipeDetailState.value =
                    _recipeDetailState.value.copy(recipeSampleQuestions = result)
            }
        }
    }

    fun getRecipeById(recipeId: String) {
        viewModelScope.launch {
            getRecipeByIdUseCase(recipeId).collect { result ->
                _recipeDetailState.value = _recipeDetailState.value.copy(recipeDetail = result)
            }
        }
    }

}