package com.example.shopify.core.network

import com.example.shopify.core.datastore.SessionManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val sessionManager: SessionManager
):Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = sessionManager.getAccessToken()
        val request = chain.request()
            .newBuilder().apply {
                if(!accessToken.isNullOrBlank()){
                    addHeader(
                        "Authorization",
                        "Bearer $accessToken"
                    )
                }
            }
            .build()
        return chain.proceed(request)
    }
}