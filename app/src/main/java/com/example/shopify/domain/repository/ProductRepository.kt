package com.example.shopify.domain.repository

import com.example.shopify.core.common.Resource
import com.example.shopify.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    fun getProducts(): Flow<Resource<List<Product>>>

    fun getProductById(
        productId: Int
    ): Flow<Resource<Product>>

    fun getProductsByCategory(
        categoryId: Int
    ): Flow<Resource<List<Product>>>


    fun searchProducts(
        query: String
    ): Flow<Resource<List<Product>>>
}