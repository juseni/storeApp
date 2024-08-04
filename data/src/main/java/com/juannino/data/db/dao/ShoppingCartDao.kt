package com.juannino.data.db.dao

import android.database.sqlite.SQLiteException
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.juannino.data.db.entities.ShoppingCartEntity
import kotlinx.coroutines.flow.Flow

/**
 * @author Juan Sebastian Niño - 2023
 */

@Dao
interface ShoppingCartDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    @Throws(SQLiteException::class)
    suspend fun insertAllProductItems(productItems: List<ShoppingCartEntity>)

    @Query("SELECT * FROM SHOPPING_CART_TABLE")
    @Throws(SQLiteException::class)
    fun getAllProductItems(): Flow<List<ShoppingCartEntity>>

    @Query("SELECT * FROM SHOPPING_CART_TABLE WHERE itemQuantity > '0'")
    @Throws(SQLiteException::class)
    fun getAllProductItemsToCheckout(): Flow<List<ShoppingCartEntity>>

    @Query("UPDATE SHOPPING_CART_TABLE SET itemQuantity = :itemQuantity," +
            " totalPriceWithDiscount = :itemPriceDiscount WHERE code == :code ")
    @Throws(SQLiteException::class)
    suspend fun updateShoppingItemByCode(
        code: String,
        itemQuantity: Int,
        itemPriceDiscount: Double
    ): Int

    @Query("SELECT T.itemQuantity FROM SHOPPING_CART_TABLE T WHERE T.itemQuantity > '0'")
    @Throws(SQLiteException::class)
    fun getQuantityItemsInCart(): Flow<List<Int>>

    @Query("UPDATE SHOPPING_CART_TABLE SET totalPriceWithDiscount = '0.0', itemQuantity = '0' ")
    @Throws(SQLiteException::class)
    suspend fun clearAllDataFromCart() : Int
}