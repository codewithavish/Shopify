package com.example.shopify.data.local.entity

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import com.example.shopify.domain.model.CartItem


@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey
    val itemId: String,
    val cartId: String,
    val productId: Int,
    val title: String,
    val thumbnail: String,
    val unitPrice: Double,
    val quantity: Int,
    val createdAt: Long,
    val updatedAt: Long
)

fun CartItemEntity.toDomain(): CartItem {
    return CartItem(
        itemId = itemId,
        productId = productId,
        title = title,
        thumbnail = thumbnail,
        unitPrice = unitPrice,
        quantity = quantity,
        itemSubtotal = unitPrice * quantity
    )
}