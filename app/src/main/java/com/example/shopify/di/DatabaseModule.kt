package com.example.shopify.di

import android.content.Context
import androidx.room3.Room
import com.example.shopify.data.local.dao.CartDao
import com.example.shopify.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "shopify_database"
        ).build()
    }


    @Provides
    @Singleton
    fun provideCartDao(
        database: AppDatabase
    ): CartDao {
        return database.cartDao()
    }

}