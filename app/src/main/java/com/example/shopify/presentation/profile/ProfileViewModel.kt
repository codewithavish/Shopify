package com.example.shopify.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopify.core.common.Resource
import com.example.shopify.domain.repository.AuthRepository
import com.example.shopify.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<ProfileUiState>(
            ProfileUiState.Loading
        )

    val uiState = _uiState.asStateFlow()


    private val _isLoggingOut =
        MutableStateFlow(false)

    val isLoggingOut = _isLoggingOut.asStateFlow()

    init {
        getProfile()
    }

    fun getProfile() {

        viewModelScope.launch {

            profileRepository.getProfile().collect { result ->

                when (result) {
                    Resource.Loading -> {
                        _uiState.value =
                            ProfileUiState.Loading
                    }

                    is Resource.Success -> {
                        _uiState.value =
                            ProfileUiState.Success(
                                user = result.data
                            )
                    }

                    is Resource.Error -> {
                        _uiState.value =
                            ProfileUiState.Error(
                                message = result.message
                            )
                    }
                }
            }
        }
    }

    fun logout(
        onSuccess: () -> Unit
    ) {

        viewModelScope.launch {

            authRepository.logout().collect { result ->

                when (result) {

                    Resource.Loading -> {
                        _isLoggingOut.value = true
                    }

                    is Resource.Success -> {
                        _isLoggingOut.value = false
                        onSuccess()
                    }

                    is Resource.Error -> {
                        _isLoggingOut.value = false
                    }
                }
            }
        }
    }
}