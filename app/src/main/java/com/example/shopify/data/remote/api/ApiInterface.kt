package com.example.shopify.data.remote.api

import com.example.shopify.data.remote.dto.CategoryDto
import com.example.shopify.data.remote.dto.LoginRequestDto
import com.example.shopify.data.remote.dto.LoginResponseDto
import com.example.shopify.data.remote.dto.ProductDto
import com.example.shopify.data.remote.dto.RegisterRequestDto
import com.example.shopify.data.remote.dto.UpdateUserRequestDto
import com.example.shopify.data.remote.dto.UserDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {


    @POST("users")
    suspend fun register(
        @Body request: RegisterRequestDto
    ): UserDto

    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequestDto
    ): LoginResponseDto

    @GET("auth/profile")
    suspend fun getProfile(
        @Header("Authorization") authorization: String
    ): UserDto


    @PUT("users/{userId}")
    suspend fun updateProfile(
        @Path("userId") userId: Int,
        @Body request: UpdateUserRequestDto
    ): UserDto

    @GET("categories")
    suspend fun getCategories(): List<CategoryDto>

    @GET("categories/{categoryId}/products")
    suspend fun getProductsByCategoryId(
        @Path("categoryId") categoryId: Int
    ): List<ProductDto>

    @GET("products")
    suspend fun getProducts(): List<ProductDto>

    @GET("products/{id}")
    suspend fun getProductById(
        @Path("id") productId: Int
    ): ProductDto

    @GET("products/")
    suspend fun getProductsByCategory(
        @Query("categoryId") categoryId: Int
    ): List<ProductDto>

    @GET("products")
    suspend fun searchProduct(
     @Query("title") title: String
    ): List<ProductDto>


}