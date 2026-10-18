package com.example.shopify.presentation.profile

import com.example.shopify.domain.model.User

sealed interface EditProfileUiState {

    data object Loading : EditProfileUiState
    data class Saving(
        val user: User
    ) : EditProfileUiState


    data class Success(
        val user: User
    ) : EditProfileUiState

    data class Error(
        val message: String
    ) : EditProfileUiState
}