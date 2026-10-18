package com.example.shopify.data.remote.dto

import com.example.shopify.domain.model.Category


data class CategoryDto(
    val id: Int,
    val name: String,
    val slug: String?,
    val image: String?
)

fun CategoryDto.toDomain(): Category {
    return Category(
        id = id,
        name = name,
        image = image
    )
}
