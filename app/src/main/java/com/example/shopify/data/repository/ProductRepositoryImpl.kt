package com.example.shopify.data.repository

import com.example.shopify.core.common.Resource
import com.example.shopify.data.remote.dto.toDomain
import com.example.shopify.data.remote.api.ApiInterface
import com.example.shopify.domain.model.Product
import com.example.shopify.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ProductRepositoryImpl @Inject constructor(
    private val apiInterface: ApiInterface
) : ProductRepository {

    override fun getProducts(): Flow<Resource<List<Product>>> =
        flow {

            emit(Resource.Loading)

            try {
                val response = apiInterface.getProducts()
                val products = response.map { it.toDomain() }
                emit(
                    Resource.Success(products)
                )

            } catch (e: Exception) {
                emit(
                    Resource.Error(
                        message = e.message
                            ?: "Unable to load products",
                        cause = e
                    )
                )
            }
        }

    override fun getProductById(
        productId: Int
    ): Flow<Resource<Product>> =
        flow {

            emit(Resource.Loading)

            try {
                val response = apiInterface.getProductById(
                    productId = productId
                )

                emit(
                    Resource.Success(
                        response.toDomain()
                    )
                )

            } catch (e: Exception) {
                emit(
                    Resource.Error(
                        message = e.message ?: "Unable to load product",
                        cause = e
                    )
                )
            }
        }

    override fun getProductsByCategory(
        categoryId: Int
    ): Flow<Resource<List<Product>>> =
        flow {

            emit(Resource.Loading)

            try {
                val response = apiInterface.getProductsByCategory(
                    categoryId = categoryId
                )

                val products = response.map { it.toDomain() }
                emit(
                    Resource.Success(products)
                )

            } catch (e: Exception) {
                emit(
                    Resource.Error(
                        message = e.message ?: "Unable to load category products",
                        cause = e
                    )
                )
            }
        }

    override fun searchProducts(
        query: String
    ): Flow<Resource<List<Product>>> = flow {

        emit(Resource.Loading)

        try {
            val response = apiInterface.getProducts()
            val products = response.map { product ->
                product.toDomain()
            }
            val result = if (query.isBlank()) {
                products
            } else {
                products.filter {
                    it.title.contains(
                        query,
                        ignoreCase = true
                    )
                }
            }

            emit(Resource.Success(result))

        } catch (e: Exception) {
            emit(
                Resource.Error(
                    message = e.message ?: "Unable to search products",
                    cause = e
                )
            )
        }
    }
}