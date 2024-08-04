package com.juannino.discounts.data

import com.juannino.discounts.ProductItemDiscounts
import kotlinx.coroutines.flow.Flow

/**
 * @author Juan Sebastian Niño - 2023
 */

interface DiscountsRepository {

    fun getDiscounts(): Flow<List<ProductItemDiscounts>>

    suspend fun getTotalPriceWithDiscount(
        code: String,
        itemQuantity: Int,
        price: Double
    ): Double
}