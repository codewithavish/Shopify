package com.example.shopify.domain.repository

import com.example.shopify.core.common.Resource
import com.example.shopify.domain.model.Cart
import com.example.shopify.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface CartRepository {

    fun observeCart(): Flow<Resource<Cart>>

    fun addToCart(product: Product): Flow<Resource<Unit>>

    fun increaseQuantity(itemId: String): Flow<Resource<Unit>>

    fun decreaseQuantity(itemId: String): Flow<Resource<Unit>>

    fun removeFromCart(itemId: String): Flow<Resource<Unit>>

    fun clearCart(): Flow<Resource<Unit>>
}