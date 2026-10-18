package com.example.shopify.data.repository

import com.example.shopify.core.common.Resource
import com.example.shopify.core.datastore.SessionManager
import com.example.shopify.data.remote.api.ApiInterface
import com.example.shopify.data.remote.dto.UpdateUserRequestDto
import com.example.shopify.data.remote.dto.toDomain
import com.example.shopify.domain.model.User
import com.example.shopify.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepositoryImpl @Inject constructor(
    private val apiInterface: ApiInterface,
    private val sessionManager: SessionManager
) : ProfileRepository {

    override fun getProfile(): Flow<Resource<User>> = flow {

        emit(Resource.Loading)

        try {
            val token = sessionManager.getAccessToken()
            if (token.isNullOrBlank()) {
                emit(
                    Resource.Error(
                        message = "User is not logged in"
                    )
                )
                return@flow
            }

            val response = apiInterface.getProfile(
                authorization = "Bearer $token"
            )
            val user = response.toDomain()

            emit(
                Resource.Success(user)
            )

        } catch (e: Exception) {
            emit(
                Resource.Error(
                    message = e.message ?: "Unable to load profile",
                    cause = e
                )
            )
        }
    }

    override fun updateProfile(
        userId: Int,
        name: String,
        email: String,
        avatar: String
    ): Flow<Result<User>> = flow {

        try {
            val request = UpdateUserRequestDto(
                name = name,
                email = email,
                avatar = avatar
            )
            val response = apiInterface.updateProfile(
                userId = userId,
                request = request
            )
            val user = response.toDomain()

            emit(
                Result.success(user)
            )

        } catch (e: Exception) {
            emit(
                Result.failure(
                    e
                )
            )
        }
    }
}