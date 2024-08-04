package com.juannino.cabifychallenge.utils

import com.juannino.domain.models.ProductItem

/**
 * @author Juan Sebastian Niño - 2023
 */

val productItemsMock = listOf(
    ProductItem(
        "VOUCHER",
        "Cabify Voucher",
        5.0,
        3,
        20.0
    ),
    ProductItem(
        "TSHIRT",
        "Cabify T-Shirt",
        20.0,
        0,
        0.0
    ),
    ProductItem(
        "MUG",
        "Cabify Coffee Mug",
        7.5,
        0,
        0.0
    )
)

val updatedProductsItemMock = listOf(
    ProductItem(
        "VOUCHER",
        "Cabify Voucher",
        5.0,
        3,
        20.0
    ),
    ProductItem(
        "TSHIRT",
        "Cabify T-Shirt",
        20.0,
        3,
        57.0
    ),
    ProductItem(
        "MUG",
        "Cabify Coffee Mug",
        7.5,
        0,
        0.0
    )
)