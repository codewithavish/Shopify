package com.example.shopify.domain.repository

import com.example.shopify.core.common.Resource
import com.example.shopify.domain.model.User
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    fun getProfile(): Flow<Resource<User>>

    fun updateProfile(
        userId: Int,
        name: String,
        email: String,
        avatar: String
    ): Flow<Result<User>>

}