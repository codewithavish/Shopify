package com.example.shopify.presentation.auth.register

import com.example.shopify.domain.model.User

sealed interface RegisterUiState {

    data object Idle : RegisterUiState
    data object Loading : RegisterUiState

    data class Success(
        val user: User
    ) : RegisterUiState

    data class Error(
        val message: String
    ): RegisterUiState
}