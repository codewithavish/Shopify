package com.example.shopify.domain.repository

import com.example.shopify.core.common.Resource
import com.example.shopify.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getCategoryList(): Flow<Resource<List<Category>>>

}