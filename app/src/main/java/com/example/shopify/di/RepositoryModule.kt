package com.example.shopify.di

import com.example.shopify.data.repository.AuthRepositoryImpl
import com.example.shopify.data.repository.CartRepositoryImpl
import com.example.shopify.data.repository.CategoryRepositoryImp
import com.example.shopify.data.repository.ProductRepositoryImpl
import com.example.shopify.data.repository.ProfileRepositoryImpl
import com.example.shopify.data.repository.SessionRepositoryImpl
import com.example.shopify.domain.repository.AuthRepository
import com.example.shopify.domain.repository.CartRepository
import com.example.shopify.domain.repository.CategoryRepository
import com.example.shopify.domain.repository.ProductRepository
import com.example.shopify.domain.repository.ProfileRepository
import com.example.shopify.domain.repository.SessionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        implementation: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindSessionRepository(
        implementation: SessionRepositoryImpl
    ): SessionRepository

    @Binds
    @Singleton
    abstract fun bindProfileRepository(
        profileRepositoryImpl: ProfileRepositoryImpl
    ): ProfileRepository


    @Binds
    @Singleton
    abstract fun bindCategoryRepository(
        categoryRepositoryImp: CategoryRepositoryImp
    ): CategoryRepository

    @Binds
    @Singleton
    abstract fun bindProductRepository(
        productRepositoryImpl: ProductRepositoryImpl
    ): ProductRepository

    @Binds
    @Singleton
    abstract fun bindCartRepository(
        repositoryImpl: CartRepositoryImpl
    ): CartRepository

}