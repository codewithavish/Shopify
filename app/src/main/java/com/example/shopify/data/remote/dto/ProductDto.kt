package com.example.shopify.data.remote.dto

import com.example.shopify.domain.model.Category
import com.example.shopify.domain.model.Product

data class ProductDto(
    val id: Int,
    val title: String,
    val slug: String?,
    val price: Double,
    val description: String?,
    val category: CategoryDto?,
    val images: List<String>?
)

fun ProductDto.toDomain(): Product {
    return Product(
        id = id,
        title = title,
        price = price,
        description = description.orEmpty(),
        category = category?.let {
            Category(
                id = it.id,
                name = it.name,
                image = it.image
            )
        },

        images = images.orEmpty(),
        thumbnail = images
            ?.firstOrNull()
            .orEmpty()
    )
}