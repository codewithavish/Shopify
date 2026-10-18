package com.example.shopify.domain.model


data class Cart (
  val id: String,
  val userId: Int?,
  val currencyCode: String,
  val items: List<CartItem>,
  val subtotal: Double,
  val discount: Double,
  val deliveryFee: Double,
  val tax: Double,
  val total: Double
)