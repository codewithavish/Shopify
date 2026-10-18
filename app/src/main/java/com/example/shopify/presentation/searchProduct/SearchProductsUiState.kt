package com.example.shopify.presentation.searchProduct

import com.example.shopify.domain.model.Product

sealed interface SearchProductsUiState {

    data object Idle : SearchProductsUiState

    data object Loading : SearchProductsUiState

    data class Success(
        val products: List<Product>
    ) : SearchProductsUiState

    data class Error(
        val message: String
    ) : SearchProductsUiState
}