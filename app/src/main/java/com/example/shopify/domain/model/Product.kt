package com.example.shopify.domain.model

data class Product(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: Category?,
    val images: List<String>,
    val thumbnail: String
)