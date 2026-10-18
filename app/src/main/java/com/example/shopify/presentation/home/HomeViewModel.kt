package com.example.shopify.presentation.home

import com.example.shopify.domain.repository.ProductRepository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopify.core.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadProducts()
    }

    private fun loadProducts() {

        viewModelScope.launch {
            productRepository.getProducts().collect { result ->
                when (result) {
                    Resource.Loading -> {
                        _uiState.value = HomeUiState.Loading
                    }

                    is Resource.Success -> {
                        val products = result.data
                        _uiState.value = HomeUiState.Success(
                            products = products,
                            categories = products
                                .mapNotNull { it.category }
                                .distinct()
                        )
                    }

                    is Resource.Error -> {
                        _uiState.value = HomeUiState.Error(
                            message = result.message
                        )
                    }
                }
            }
        }
    }
}