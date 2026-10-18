package com.example.shopify.data.remote.dto


data class RegisterRequestDto(
    val name: String,
    val email: String,
    val password: String,
    val avatar: String
)