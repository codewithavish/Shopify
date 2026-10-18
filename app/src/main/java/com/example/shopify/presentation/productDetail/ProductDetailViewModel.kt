package com.example.shopify.presentation.productDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopify.core.common.Resource
import com.example.shopify.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<ProductDetailUiState>(
            ProductDetailUiState.Loading
        )

    val uiState: StateFlow<ProductDetailUiState> = _uiState.asStateFlow()

    fun getProduct(productId: Int) {

        viewModelScope.launch {

            productRepository
                .getProductById(productId)
                .collect { result ->

                    _uiState.value = when (result) {

                        Resource.Loading ->
                            ProductDetailUiState.Loading

                        is Resource.Success ->
                            ProductDetailUiState.Success(
                                product = result.data
                            )

                        is Resource.Error ->
                            ProductDetailUiState.Error(
                                message = result.message
                            )
                    }
                }
        }
    }
}