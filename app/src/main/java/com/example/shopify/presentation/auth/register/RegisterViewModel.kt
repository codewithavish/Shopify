package com.example.shopify.presentation.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopify.core.common.Resource
import com.example.shopify.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<RegisterUiState>(RegisterUiState.Idle)
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun register(name: String, email: String, password: String) {

        when {
            name.isBlank() -> {
                _uiState.value =
                    RegisterUiState.Error(
                        "Name is required"
                    )
                return
            }

            email.isBlank() -> {
                _uiState.value =
                    RegisterUiState.Error(
                        "Email is required"
                    )
                return
            }

            !android.util.Patterns.EMAIL_ADDRESS
                .matcher(email)
                .matches() -> {

                _uiState.value =
                    RegisterUiState.Error(
                        "Enter a valid email address"
                    )

                return
            }

            password.length < 6 -> {
                _uiState.value =
                    RegisterUiState.Error(
                        "Password must contain at least 6 characters"
                    )
                return
            }
        }

        viewModelScope.launch {
            authRepository.register(
                name = name,
                email = email,
                password = password
            ).collect { result ->

                when (result) {
                    Resource.Loading -> {
                        _uiState.value = RegisterUiState.Loading
                    }

                    is Resource.Success -> {
                        _uiState.value =
                            RegisterUiState.Success(
                                user = result.data
                            )
                    }

                    is Resource.Error -> {
                        _uiState.value =
                            RegisterUiState.Error(
                                message = result.message
                            )
                    }
                }
            }
        }
    }
}