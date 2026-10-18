package com.example.shopify.presentation.productDetail

import com.example.shopify.domain.model.Product

sealed interface ProductDetailUiState {

    data object Loading : ProductDetailUiState

    data class Success(
        val product: Product
    ) : ProductDetailUiState

    data class Error(
        val message: String
    ) : ProductDetailUiState
}