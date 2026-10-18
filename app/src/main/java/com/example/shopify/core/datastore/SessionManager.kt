package com.example.shopify.core.datastore

import android.content.Context
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(
    @ApplicationContext context: Context
) {

    private val preferences = context.getSharedPreferences(
        PREF_NAME,
        Context.MODE_PRIVATE
    )

    fun saveSession(
        accessToken: String,
        refreshToken: String?
    ) {
        preferences.edit {
            putString(
                KEY_ACCESS_TOKEN,
                accessToken
            )

            putString(
                KEY_REFRESH_TOKEN,
                refreshToken
            )
        }
    }

    fun getAccessToken(): String? {
        return preferences.getString(
            KEY_ACCESS_TOKEN,
            null
        )
    }

    fun getRefreshToken(): String? {
        return preferences.getString(
            KEY_REFRESH_TOKEN,
            null
        )
    }

    fun isLoggedIn(): Boolean {
        return !getAccessToken().isNullOrBlank()
    }

    fun clearSession() {
        preferences.edit {
            clear()
        }
    }

    companion object {
        private const val PREF_NAME = "shopify_session"
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
    }
}