package com.juannino.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.juannino.data.model.ProductItem

/**
 * @author Juan Sebastian Niño - 2023
 */

@Entity(tableName = "shopping_cart_table")
data class ShoppingCartEntity(
    @PrimaryKey(autoGenerate = false)
    val code: String,
    val itemName: String,
    val itemPrice: Double,
    // the below fields represent the quantity and total price of an specific item added to the bag
    val itemQuantity: Int = 0,
    val totalPriceWithDiscount: Double = 0.0
)

fun ShoppingCartEntity.asExternalModel() = ProductItem(
    code = code,
    name = itemName,
    price = itemPrice,
    itemQuantity = itemQuantity,
    totalPriceItems = totalPriceWithDiscount
)