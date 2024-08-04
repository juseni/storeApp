package com.juannino.data.repositories

import com.juannino.data.model.ProductItem
import kotlinx.coroutines.flow.Flow

/**
 * @author Juan Sebastian Niño - 2023
 */

interface ShoppingCartRepository {

    fun getShoppingCartItemProducts(): Flow<Result<List<ProductItem>>>

    suspend fun makeShoppingCartPayment(): Boolean

    fun getItemsQuantityInCart(): Flow<List<Int>>
}