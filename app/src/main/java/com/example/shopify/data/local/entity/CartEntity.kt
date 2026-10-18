package com.example.shopify.data.local.entity

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import com.example.shopify.domain.model.Cart


@Entity(tableName = "cart")
data class CartEntity(
    @PrimaryKey
    val cartId: String,
    val userId: Int?,
    val currencyCode: String,
    val createdAt: Long,
    val updatedAt: Long
)

fun CartEntity.toDomain(
    items: List<CartItemEntity>
): Cart {

    val domainItems = items.map {
        it.toDomain()
    }

    val subtotal = domainItems.sumOf {
        it.itemSubtotal
    }

    val discount = 0.0
    val deliveryFee = if (subtotal > 0) {
        10.0
    } else {
        0.0
    }

    val tax = subtotal * 0.05
    val total = subtotal - discount + deliveryFee + tax
    return Cart(
        id = cartId,
        userId = userId,
        currencyCode = currencyCode,
        items = domainItems,
        subtotal = subtotal,
        discount = discount,
        deliveryFee = deliveryFee,
        tax = tax,
        total = total
    )
}


