package com.example.shopify.data.local.database

import androidx.room3.Database
import androidx.room3.RoomDatabase
import com.example.shopify.data.local.dao.CartDao
import com.example.shopify.data.local.entity.CartEntity
import com.example.shopify.data.local.entity.CartItemEntity

@Database(
    entities = [
        CartEntity::class,
        CartItemEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase(){
    abstract fun cartDao(): CartDao
}