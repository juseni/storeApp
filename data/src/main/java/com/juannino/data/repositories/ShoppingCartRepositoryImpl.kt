package com.juannino.data.repositories

import android.database.sqlite.SQLiteException
import com.juannino.data.db.dao.ShoppingCartDao
import com.juannino.data.db.entities.ShoppingCartEntity
import com.juannino.data.db.entities.asExternalModel
import com.juannino.data.model.ProductItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * @author Juan Sebastian Niño - 2023
 */

class ShoppingCartRepositoryImpl @Inject constructor(
    private val shoppingCartDao: ShoppingCartDao
) : ShoppingCartRepository {

    override fun getShoppingCartItemProducts(): Flow<Result<List<ProductItem>>> =
        try {
            shoppingCartDao.getAllProductItemsToCheckout().map {
                Result.success(it.map(ShoppingCartEntity::asExternalModel))
            }
        } catch (ex: SQLiteException) {
            flow { emit(Result.failure(ex)) }
        }

    override suspend fun makeShoppingCartPayment(): Boolean =
        try {
            shoppingCartDao.clearAllDataFromCart() > 0
        } catch (ex: SQLiteException) {
            false
        }

    override fun getItemsQuantityInCart(): Flow<List<Int>> =
        try {
            shoppingCartDao.getQuantityItemsInCart()
        } catch (ex: SQLiteException) {
            flow { emit(listOf(Int.MIN_VALUE)) }
        }
}