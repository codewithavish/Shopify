package com.example.shopify.domain.model


class CartItem (
    val itemId: String,
    val productId: Int,
    val title: String,
    val thumbnail: String,
    val unitPrice: Double,
    val quantity: Int,
    val itemSubtotal: Double
)