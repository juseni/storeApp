package com.juannino.data.model

/**
 * @author Juan Sebastian Niño - 2023
 */

data class ProductItem(
    val code: String,
    val name: String,
    val price: Double,
    val itemQuantity: Int,
    val totalPriceItems: Double
)