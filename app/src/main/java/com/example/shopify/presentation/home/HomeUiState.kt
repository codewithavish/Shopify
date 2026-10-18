package com.example.shopify.presentation.home

import com.example.shopify.domain.model.Category
import com.example.shopify.domain.model.Product

sealed interface HomeUiState {

    data object Loading : HomeUiState

    data class Success(
        val products: List<Product>,
        val categories: List<Category>
    ) : HomeUiState

    data class Error(
        val message: String
    ) : HomeUiState
}
