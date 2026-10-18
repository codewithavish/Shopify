package com.example.shopify.presentation.cart

import com.example.shopify.domain.model.Cart

sealed interface CartUiState {

    data object Loading : CartUiState

    data class Success(
        val cart: Cart
    ) : CartUiState

    data class Error(
        val message: String
    ) : CartUiState
}