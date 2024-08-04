package com.juannino.domain.models

/**
 * @author Juan Sebastian Niño - 2023
 */

data class UpdateProductItem(
    val code: String,
    val price: Double,
    val quantityItems: Int,
    val discounts: ProductItemDiscounts?
)