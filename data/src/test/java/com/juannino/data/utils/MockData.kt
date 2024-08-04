package com.juannino.data.utils

import com.juannino.data.db.entities.ShoppingCartEntity
import com.juannino.data.model.ProductItem
import com.juannino.data.network.models.NetworkProduct

/**
 * @author Juan Sebastian Niño - 2023
 */

val apiProductsMock = listOf(
    NetworkProduct("VOUCHER", "Cabify Voucher", 5.0),
    NetworkProduct("TSHIRT", "Cabify T-Shirt", 20.0),
    NetworkProduct("MUG", "Cabify Coffee Mug", 7.5),
)
val shoppingCartEntityMock = ShoppingCartEntity(
    "VOUCHER",
    "Cabify Voucher",
    5.0,
    3,
    10.0
)
val shoppingCartEntityMock2 = ShoppingCartEntity(
    "TSHIRT",
    "Cabify T-Shirt",
    20.0,
    0,
    0.0
)

val productItemsFirstTime = listOf(
    ProductItem("VOUCHER", "Cabify Voucher", 5.0, 0, 0.0),
    ProductItem("TSHIRT", "Cabify T-Shirt", 20.0, 0, 0.0),
    ProductItem("MUG", "Cabify Coffee Mug", 7.5, 0, 0.0)
)

val productItemsMock = listOf(
    ProductItem(
        "VOUCHER",
        "Cabify Voucher",
        5.0,
        3,
        10.0
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