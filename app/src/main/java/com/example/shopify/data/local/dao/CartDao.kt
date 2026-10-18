package com.example.shopify.data.local.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import com.example.shopify.data.local.entity.CartEntity
import com.example.shopify.data.local.entity.CartItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Query("SELECT * FROM cart LIMIT 1")
    suspend fun getCart(): CartEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCart(cart: CartEntity)

    @Query("SELECT * FROM cart LIMIT 1")
    fun observeCart(): Flow<CartEntity?>


    @Query(
        """
        SELECT * FROM cart_items
        WHERE cartId = :cartId
        ORDER BY createdAt DESC
    """
    )
    fun observeCartItems(
        cartId: String
    ): Flow<List<CartItemEntity>>

    @Query(
        """
        SELECT * FROM cart_items
        WHERE cartId = :cartId
        AND productId = :productId
        LIMIT 1
    """
    )
    suspend fun getCartItem(
        cartId: String,
        productId: Int
    ): CartItemEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(
        item: CartItemEntity
    )

    @Query(
        """
        UPDATE cart_items
        SET quantity = :quantity,
            updatedAt = :updatedAt
        WHERE itemId = :itemId
    """
    )
    suspend fun updateQuantity(
        itemId: String,
        quantity: Int,
        updatedAt: Long
    )

    @Query(
        """
        DELETE FROM cart_items
        WHERE itemId = :itemId
    """
    )
    suspend fun deleteCartItem(
        itemId: String
    )

    @Query(
    """
    SELECT * FROM cart_items
    WHERE itemId = :itemId
    LIMIT 1
    """
    )
    suspend fun getCartItemById(
        itemId: String
    ): CartItemEntity?

    @Query(
        """
        DELETE FROM cart_items
        WHERE cartId = :cartId
    """
    )
    suspend fun clearCartItems(
        cartId: String
    )
}