package com.example.shopify.presentation.auth.login

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
class LoginViewModel @Inject constructor(
    val repository: AuthRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun login(email: String, password: String) {
        if (email.isBlank()) {
            _uiState.value = LoginUiState.Error("Username is required")
            return
        }

        if (password.isBlank()) {
            _uiState.value = LoginUiState.Error("Password is required")
            return
        }

        viewModelScope.launch {
            repository.login(
                email = email.trim(),
                password = password
            ).collect { result ->
                when (result) {
                    Resource.Loading -> {
                        _uiState.value = LoginUiState.Loading
                    }

                    is Resource.Success -> {
                        _uiState.value =
                            LoginUiState.Success(
                                result.data
                            )

                    }

                    is Resource.Error -> {
                        _uiState.value =
                            LoginUiState.Error(
                                result.message
                            )

                    }
                }

            }
        }

    }
}