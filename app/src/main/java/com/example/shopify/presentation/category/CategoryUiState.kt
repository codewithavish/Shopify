package com.example.shopify.presentation.category

import com.example.shopify.domain.model.Category

sealed interface CategoryUiState {

    data object Loading : CategoryUiState

    data class Success(
        val categoryList : List<Category>
    ) : CategoryUiState

    data class Error(
        val message: String
    ): CategoryUiState

}