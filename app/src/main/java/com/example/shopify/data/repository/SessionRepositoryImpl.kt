package com.example.shopify.data.repository


import com.example.shopify.core.datastore.SessionManager
import com.example.shopify.domain.repository.SessionRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionRepositoryImpl @Inject constructor(
    private val sessionManager: SessionManager
) : SessionRepository {

    override fun getAccessToken(): String? =
        sessionManager.getAccessToken()

    override fun getRefreshToken(): String? =
        sessionManager.getRefreshToken()

    override fun isLoggedIn(): Boolean =
        sessionManager.isLoggedIn()

    override fun clearSession() {
        sessionManager.clearSession()
    }
}