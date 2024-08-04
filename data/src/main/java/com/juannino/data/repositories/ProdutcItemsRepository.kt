package com.juannino.data.repositories

import com.juannino.data.model.ProductItem
import kotlinx.coroutines.flow.Flow

/**
 * @author Juan Sebastian Niño - 2023
 */

interface ProductItemsRepository {

    fun getAllProductItems(): Flow<Result<List<ProductItem>>>

    suspend fun updateProductItem(
        code: String,
        itemsQuantity: Int,
        newPrice: Double
    ): Boolean
}