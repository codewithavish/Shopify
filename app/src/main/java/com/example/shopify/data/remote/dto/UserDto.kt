package com.example.shopify.data.remote.dto

import com.example.shopify.domain.model.User
import kotlin.Int

data class UserDto(
    val id: Int,
    val name: String,
    val email: String,
    val password: String? = null,
    val avatar: String? = null
)

fun UserDto.toDomain(): User {
    return User(
        id = id,
        name = name,
        email = email,
        avatar = avatar
    )
}

