package com.example.myapplication.arunproject.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.arunproject.common.AppConstants.NO_INTERNET_ERROR_MESSAGE
import com.example.myapplication.arunproject.domain.usecase.GetRecipesUseCase
import com.example.myapplication.arunproject.presentation.view.state.RecipeViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val getRecipesUseCase: GetRecipesUseCase,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _recipeState = MutableStateFlow(RecipeViewState())
    val recipeState: StateFlow<RecipeViewState> = _recipeState.asStateFlow()

    init {
        loadRecipes()
    }

    fun loadRecipes() {
        viewModelScope.launch(ioDispatcher) {
            _recipeState.value = _recipeState.value.copy(isLoading = true)
            try {
                getRecipesUseCase().collect { recipes ->
                    _recipeState.value =
                        _recipeState.value.copy(recipes = recipes, isLoading = false)
                }
            } catch (e: Exception) {
                _recipeState.value = _recipeState.value.copy(
                    errorMessage = NO_INTERNET_ERROR_MESSAGE,
                    isLoading = false
                )
            }
        }
    }

    fun onRecipeSelected(selectedIdFromState: String?, recipeId: String) {
        if (selectedIdFromState == recipeId) {
            _recipeState.value =
                _recipeState.value.copy(
                    selectedRecipeId = null,
                    isAddToCartButtonEnabled = false,
                    isViewInfoButtonEnabled = false
                )
        } else {
            _recipeState.value =
                _recipeState.value.copy(
                    selectedRecipeId = recipeId,
                    isAddToCartButtonEnabled = true, isViewInfoButtonEnabled = true
                )
        }
    }

    fun addToCart(recipeId: String) {
        val cart = _recipeState.value.cart.toMutableList()
        cart.add(recipeId)
        _recipeState.value = _recipeState.value.copy(cart = cart)
    }

    fun clearCart() {
        _recipeState.value = _recipeState.value.copy(cart = emptyList())
    }

    fun clearSelectedRecipeId() {
        _recipeState.value = _recipeState.value.copy(selectedRecipeId = null)
        _recipeState.value =
            _recipeState.value.copy(isAddToCartButtonEnabled = true, isViewInfoButtonEnabled = true)
    }

    fun showGridList(isShowGrid: Boolean) {
        _recipeState.value = _recipeState.value.copy(isShowGrid = isShowGrid)
    }

    fun searchRecipe(query: String) {
        _recipeState.value = _recipeState.value.copy(searchQuery = query)
    }
}