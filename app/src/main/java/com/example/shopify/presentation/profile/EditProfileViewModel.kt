package com.example.shopify.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopify.core.common.Resource
import com.example.shopify.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<EditProfileUiState>(EditProfileUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getProfile()
    }

    fun getProfile() {
        viewModelScope.launch {
            profileRepository.getProfile().collect { result ->

                when (result) {
                    Resource.Loading -> {
                        _uiState.value =
                            EditProfileUiState.Loading
                    }

                    is Resource.Success -> {
                        _uiState.value =
                            EditProfileUiState.Success(
                                user = result.data
                            )
                    }

                    is Resource.Error -> {
                        _uiState.value =
                            EditProfileUiState.Error(
                                message = result.message
                            )
                    }
                }
            }
        }
    }

    fun updateProfile(
        name: String,
        email: String,
        avatar: String
    ) {

        val currentState = _uiState.value

        if (currentState !is EditProfileUiState.Success) {
            return
        }

        viewModelScope.launch {
            profileRepository.updateProfile(
                currentState.user.id,
                name,
                email,
                avatar
            ).collect { result ->
                result.onSuccess { user ->
                    _uiState.value = EditProfileUiState.Success(
                        user
                    )
                }.onFailure { exception ->
                    _uiState.value = EditProfileUiState.Error(
                        exception.message ?: "Something went wrong"
                    )
                }

            }
        }
    }
}