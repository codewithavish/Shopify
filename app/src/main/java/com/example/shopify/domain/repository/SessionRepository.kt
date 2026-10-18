package com.example.shopify.domain.repository

interface SessionRepository {

    fun getAccessToken(): String?

    fun getRefreshToken(): String?

    fun isLoggedIn(): Boolean

    fun clearSession()
}