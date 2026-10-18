package com.example.shopify.presentation.product

import com.example.shopify.domain.model.Product

sealed interface ProductUiState {

    data object Loading : ProductUiState

    data class Success(
       val product: List<Product>
    ) : ProductUiState

    data class Error(
       val message: String
    ) : ProductUiState
}