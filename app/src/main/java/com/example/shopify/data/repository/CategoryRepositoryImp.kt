package com.example.shopify.data.repository

import com.example.shopify.core.common.Resource
import com.example.shopify.data.remote.dto.toDomain
import com.example.shopify.data.remote.api.ApiInterface
import com.example.shopify.domain.model.Category
import com.example.shopify.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.collections.map

class CategoryRepositoryImp @Inject constructor(
  private val apiInterface: ApiInterface
): CategoryRepository {

    override fun getCategoryList(): Flow<Resource<List<Category>>> {
        return flow {
            try {
                val response = apiInterface.getCategories()
                val categories = response.map {
                    it.toDomain()
                }
                emit(
                    Resource.Success(categories)
                )
            }catch (e: Exception){
                emit(
                    Resource.Error(
                        e.message?:"Unable to load category",
                         e.cause
                    )
                )
            }
        }
    }
}