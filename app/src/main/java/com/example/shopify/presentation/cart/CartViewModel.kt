package com.example.shopify.presentation.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopify.core.common.Resource
import com.example.shopify.domain.model.Product
import com.example.shopify.domain.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {

    val uiState: StateFlow<CartUiState> =
        cartRepository.observeCart()
            .map { result ->
                when (result) {
                    Resource.Loading ->
                        CartUiState.Loading

                    is Resource.Success ->
                        CartUiState.Success(result.data)

                    is Resource.Error ->
                        CartUiState.Error(result.message)
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = CartUiState.Loading
            )

    fun addToCart(product: Product) {
        viewModelScope.launch {
            cartRepository.addToCart(product).collect()
        }
    }

    fun increaseQuantity(itemId: String) {
        viewModelScope.launch {
            cartRepository.increaseQuantity(itemId).collect()
        }
    }

    fun decreaseQuantity(itemId: String) {
        viewModelScope.launch {
            cartRepository.decreaseQuantity(itemId).collect()
        }
    }

    fun removeItem(itemId: String) {
        viewModelScope.launch {
            cartRepository.removeFromCart(itemId).collect()
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            cartRepository.clearCart().collect()
        }
    }
}