package com.juannino.domain.utils

import com.juannino.data.model.ProductItem
import com.juannino.discounts.ProductItemDiscounts
import com.juannino.discounts.REDUCE_ONE_WITH_THREE_OR_MORE_PROM
import com.juannino.discounts.TWO_FOR_ONE_PROM
import com.juannino.domain.models.ProductItem as DomainProductItem

/**
 * @author Juan Sebastian Niño - 2023
 */

val productItemsDataMock = listOf(
    ProductItem("VOUCHER", "Cabify Voucher", 5.0, 3, 30.0),
    ProductItem("TSHIRT", "Cabify T-Shirt", 20.0, 0, 0.0),
    ProductItem("MUG", "Cabify Coffee Mug", 7.5, 2, 40.0)
)

val productItemsDomainMock = listOf(
    DomainProductItem(
        "VOUCHER",
        "Cabify Voucher",
        5.0,
        0,
        0.0
    ),
    DomainProductItem(
        "TSHIRT",
        "Cabify T-Shirt",
        20.0,
        0,
        0.0
    ),
    DomainProductItem(
        "MUG",
        "Cabify Coffee Mug",
        7.5,
        0,
        0.0
    )
)

val productItemsDiscountMock =  listOf(
    ProductItemDiscounts(
        code = TWO_FOR_ONE_PROM,
        productItemCode = listOf("VOUCHER"),
        messagePromotion = "Buy two items get 1 for free"
    ),
    ProductItemDiscounts(
        code = REDUCE_ONE_WITH_THREE_OR_MORE_PROM,
        productItemCode = listOf("TSHIRT"),
        messagePromotion = "Buy three or more items and get 1€ discount in all items"
    )
)