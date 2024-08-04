package com.juannino.domain.models

import com.juannino.discounts.ProductItemDiscounts as DiscountItem
import com.juannino.data.model.ProductItem as DataProductItem

/**
 * @author Juan Sebastian Niño - 2023
 */

data class ProductItem(
    val code: String,
    val name: String,
    val price: Double,
    val itemQuantity: Int = 0,
    val totalPriceItems: Double = 0.0,
    val hasDiscountActive: Boolean = false,
    val discount: ProductItemDiscounts? = null
)

data class ProductItemDiscounts(
    val code: String,
    val messageDiscount: String
)

fun DataProductItem.asDomainModel(discount: DiscountItem? = null) = ProductItem(
    code = code,
    name = name,
    price = price,
    itemQuantity = itemQuantity,
    totalPriceItems = totalPriceItems,
    hasDiscountActive = itemQuantity >= 3 && discount != null,
    discount = discount?.let {
        ProductItemDiscounts(
            code = it.code,
            messageDiscount = it.messagePromotion
        )
    }
)