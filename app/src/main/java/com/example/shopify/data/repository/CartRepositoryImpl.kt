package com.example.shopify.data.repository

import com.example.shopify.core.common.Resource
import com.example.shopify.data.local.dao.CartDao
import com.example.shopify.data.local.entity.CartEntity
import com.example.shopify.data.local.entity.CartItemEntity
import com.example.shopify.data.local.entity.toDomain
import com.example.shopify.domain.model.Cart
import com.example.shopify.domain.model.Product
import com.example.shopify.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao
) : CartRepository {

    override fun observeCart(): Flow<Resource<Cart>> {
        return cartDao.observeCart()
            .flatMapLatest { cart ->
                if (cart == null) {
                    flowOf(
                        Resource.Success(emptyCart())
                    )
                } else {
                    cartDao.observeCartItems(cart.cartId)
                        .map { items ->
                            Resource.Success(
                                cart.toDomain(items)
                            )
                        }
                }
            }
    }

    override fun addToCart(
        product: Product
    ): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading)

        try {
            var cart = cartDao.getCart()
            if (cart == null) {
                val now = System.currentTimeMillis()
                cart = CartEntity(
                    cartId = UUID.randomUUID().toString(),
                    userId = null,
                    currencyCode = "USD",
                    createdAt = now,
                    updatedAt = now
                )
                cartDao.insertCart(cart)
            }

            val existingItem = cartDao.getCartItem(
                cartId = cart.cartId,
                productId = product.id
            )

            val now = System.currentTimeMillis()
            if (existingItem != null) {
                cartDao.updateQuantity(
                    itemId = existingItem.itemId,
                    quantity = existingItem.quantity + 1,
                    updatedAt = now
                )

            } else {
                val cartItem = CartItemEntity(
                    itemId = UUID.randomUUID().toString(),
                    cartId = cart.cartId,
                    productId = product.id,
                    title = product.title,
                    thumbnail = product.thumbnail,
                    unitPrice = product.price,
                    quantity = 1,
                    createdAt = now,
                    updatedAt = now
                )

                cartDao.insertCartItem(cartItem)
            }

            emit(Resource.Success(Unit))

        } catch (e: Exception) {
            emit(
                Resource.Error(
                    message = e.message ?: "Unable to add item to cart",
                    cause = e
                )
            )
        }
    }

    override fun increaseQuantity(
        itemId: String
    ): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading)

        try {
            val item = cartDao.getCartItemById(itemId)
            if (item == null) {
                emit(
                    Resource.Error(
                        message = "Cart item not found"
                    )
                )
                return@flow
            }

            cartDao.updateQuantity(
                itemId = itemId,
                quantity = item.quantity + 1,
                updatedAt = System.currentTimeMillis()
            )
            emit(Resource.Success(Unit))

        } catch (e: Exception) {
            emit(
                Resource.Error(
                    message = e.message ?: "Unable to increase quantity",
                    cause = e
                )
            )
        }
    }

    override fun decreaseQuantity(
        itemId: String
    ): Flow<Resource<Unit>> = flow {

        emit(Resource.Loading)

        try {
            val item = cartDao.getCartItemById(itemId)
            if (item == null) {
                emit(
                    Resource.Error(
                        message = "Cart item not found"
                    )
                )
                return@flow
            }

            if (item.quantity <= 1) {
                cartDao.deleteCartItem(itemId)
            } else {
                cartDao.updateQuantity(
                    itemId = itemId,
                    quantity = item.quantity - 1,
                    updatedAt = System.currentTimeMillis()
                )
            }

            emit(Resource.Success(Unit))

        } catch (e: Exception) {
            emit(
                Resource.Error(
                    message = e.message ?: "Unable to decrease quantity",
                    cause = e
                )
            )
        }
    }

    override fun removeFromCart(
        itemId: String
    ): Flow<Resource<Unit>> = flow {

        emit(Resource.Loading)

        try {
            cartDao.deleteCartItem(itemId)
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(
                Resource.Error(
                    message = e.message ?: "Unable to remove item",
                    cause = e
                )
            )
        }
    }

    override fun clearCart(): Flow<Resource<Unit>> = flow {

        emit(Resource.Loading)

        try {
            val cart = cartDao.getCart()
            if (cart != null) {
                cartDao.clearCartItems(cart.cartId)
            }
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(
                Resource.Error(
                    message = e.message ?: "Unable to clear cart",
                    cause = e
                )
            )
        }
    }

    private fun emptyCart(): Cart {
        return Cart(
            id = "",
            userId = null,
            currencyCode = "USD",
            items = emptyList(),
            subtotal = 0.0,
            discount = 0.0,
            deliveryFee = 0.0,
            tax = 0.0,
            total = 0.0
        )
    }
}