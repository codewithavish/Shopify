package com.example.shopify.data.repository

import com.example.shopify.core.common.Resource
import com.example.shopify.core.datastore.SessionManager
import com.example.shopify.data.remote.api.ApiInterface
import com.example.shopify.data.remote.dto.LoginRequestDto
import com.example.shopify.data.remote.dto.RegisterRequestDto
import com.example.shopify.data.remote.dto.toDomain
import com.example.shopify.domain.model.User
import com.example.shopify.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val apiInterface: ApiInterface,
    private val sessionManager: SessionManager
) : AuthRepository {

    override fun login(
        email: String,
        password: String
    ): Flow<Resource<User>> = flow {

        emit(Resource.Loading)

        try {
            val loginResponse = apiInterface.login(
                request = LoginRequestDto(
                    email = email.trim(),
                    password = password
                )
            )

            sessionManager.saveSession(
                accessToken = loginResponse.access_token,
                refreshToken = loginResponse.refresh_token
            )

            val profileResponse = apiInterface.getProfile(
                authorization = "Bearer ${loginResponse.access_token}"
            )

            emit(
                Resource.Success(
                    profileResponse.toDomain()
                )
            )

        } catch (e: Exception) {
            emit(
                Resource.Error(
                    message = e.message ?: "Login failed",
                    cause = e
                )
            )
        }
    }

    override fun register(
        name: String,
        email: String,
        password: String
    ): Flow<Resource<User>> = flow {

        emit(Resource.Loading)

        try {
            val response = apiInterface.register(
                request = RegisterRequestDto(
                    name = name.trim(),
                    email = email.trim(),
                    password = password,
                    avatar = DEFAULT_AVATAR
                )
            )

            emit(
                Resource.Success(
                    response.toDomain()
                )
            )

        } catch (e: Exception) {
            emit(
                Resource.Error(
                    message = e.message ?: "Registration failed",
                    cause = e
                )
            )
        }
    }

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
            emit(
                Resource.Success(
                    response.toDomain()
                )
            )

        } catch (e: Exception) {
            emit(
                Resource.Error(
                    message = e.message
                        ?: "Unable to load profile",
                    cause = e
                )
            )
        }
    }

    override fun logout(): Flow<Resource<Unit>> = flow {

        emit(Resource.Loading)

        try {
            sessionManager.clearSession()
            emit(
                Resource.Success(Unit)
            )

        } catch (e: Exception) {
            emit(
                Resource.Error(
                    message = e.message ?: "Logout failed",
                    cause = e
                )
            )
        }
    }

    companion object {
        private const val DEFAULT_AVATAR = "https://api.dicebear.com/7.x/avataaars/svg"
    }
}