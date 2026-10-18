package com.example.shopify.presentation.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopify.core.common.Resource
import com.example.shopify.domain.model.Product
import com.example.shopify.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProductUiState>(ProductUiState.Loading)

    val uiState: StateFlow<ProductUiState> = _uiState.asStateFlow()


    private val _sortOption = MutableStateFlow(ProductSortOption.DEFAULT)

    val sortOption: StateFlow<ProductSortOption> = _sortOption.asStateFlow()


    fun onSortOptionChange(option: ProductSortOption) {
        _sortOption.value = option
    }

    fun getFilterProduct(
        productList: List<Product>,
        sortOption: ProductSortOption
    ): List<Product> {
        return when (sortOption) {
            ProductSortOption.DEFAULT -> {
                productList
            }

            ProductSortOption.PRICE_LOW_TO_HIGH -> {
                productList.sortedBy {
                    it.price
                }
            }

            ProductSortOption.PRICE_HIGH_TO_LOW -> {
                productList.sortedByDescending {
                    it.price
                }
            }

            ProductSortOption.NAME_A_TO_Z -> {
                productList.sortedBy {
                    it.title
                }
            }

            ProductSortOption.NAME_Z_TO_A -> {
                productList.sortedByDescending {
                    it.title
                }
            }
        }

    }

    fun getProducts() {
        _uiState.value = ProductUiState.Loading
        viewModelScope.launch {
            productRepository.getProducts().collect { result ->
                when (result) {
                    Resource.Loading -> {
                        _uiState.value = ProductUiState.Loading
                    }

                    is Resource.Success -> {
                        _uiState.value =
                            ProductUiState.Success(result.data)
                    }

                    is Resource.Error -> {
                        _uiState.value =
                            ProductUiState.Error(
                                message = result.message
                            )
                    }
                }
            }
        }
    }

    fun getProductsByCategory(categoryId: Int) {
        _uiState.value = ProductUiState.Loading
        viewModelScope.launch {
            productRepository.getProductsByCategory(categoryId).collect { result ->
                when (result) {
                    Resource.Loading -> {
                        _uiState.value =
                            ProductUiState.Loading
                    }

                    is Resource.Success -> {
                        _uiState.value =
                            ProductUiState.Success(result.data)
                    }

                    is Resource.Error -> {
                        _uiState.value =
                            ProductUiState.Error(
                                message = result.message
                            )
                    }
                }
            }
        }
    }
}