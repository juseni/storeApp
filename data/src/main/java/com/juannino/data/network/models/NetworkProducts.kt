package com.juannino.data.network.models

import com.google.gson.annotations.SerializedName
import com.juannino.data.db.entities.ShoppingCartEntity

/**
 * @author Juan Sebastian Niño - 2023
 */

data class NetworkProducts(
    @SerializedName("products") val products: List<NetworkProduct> = emptyList()
)

data class NetworkProduct(
    @SerializedName("code") val code: String,
    @SerializedName("name") val name: String,
    @SerializedName("price") val price: Double
)

fun NetworkProduct.asEntity() = ShoppingCartEntity(
    code = code,
    itemName = name,
    itemPrice = price
)