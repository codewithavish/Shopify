package com.example.shopify.domain.repository

import com.example.shopify.core.common.Resource
import com.example.shopify.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun login(
        email: String,
        password: String
    ): Flow<Resource<User>>

    fun register(
        name: String,
        email: String,
        password: String
    ): Flow<Resource<User>>

    fun getProfile(): Flow<Resource<User>>

    fun logout(): Flow<Resource<Unit>>
}