package com.juannino.data.repositories

import android.database.sqlite.SQLiteException
import com.juannino.data.db.dao.ShoppingCartDao
import com.juannino.data.db.entities.ShoppingCartEntity
import com.juannino.data.db.entities.asExternalModel
import com.juannino.data.model.ProductItem
import com.juannino.data.network.ProductItemsNetworkDataSource
import com.juannino.data.network.models.NetworkProduct
import com.juannino.data.network.models.asEntity
import com.juannino.data.platform.NetworkHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

/**
 * @author Juan Sebastian Niño - 2023
 */

class ProductItemsRepositoryImpl @Inject constructor(
    private val networkHandler: NetworkHandler,
    private val networkDataSource: ProductItemsNetworkDataSource,
    private val shoppingCartDao: ShoppingCartDao
) : ProductItemsRepository {

    private val productItemsMutex = Mutex()

    override fun getAllProductItems(): Flow<Result<List<ProductItem>>> =
        if (networkHandler.isConnected()) {
            getProductItemsApi()
        } else {
            getProductItemsDb()
        }

    override suspend fun updateProductItem(
        code: String,
        itemsQuantity: Int,
        newPrice: Double
    ): Boolean =
        try {
            shoppingCartDao.updateShoppingItemByCode(
                code = code,
                itemQuantity = itemsQuantity,
                itemPriceDiscount = newPrice
            ) == 1
        } catch (ex: SQLiteException) {
            false
        }

    private fun getProductItemsApi(): Flow<Result<List<ProductItem>>> =
        flow {
            val apiResult = networkDataSource.getAllProductItems()
            if (apiResult.isSuccess) {
                val productItemsEntity =
                    apiResult.getOrDefault(emptyList()).map(NetworkProduct::asEntity)
                // Persist data into DB
                productItemsMutex.withLock {
                    shoppingCartDao.insertAllProductItems(productItemsEntity)
                }
                emitAll(productItemsMutex.withLock { getProductItemsDb() })
            } else {
                emit(
                    Result.failure(
                        apiResult.exceptionOrNull() ?: Throwable("Something went wrong")
                    )
                )
            }
        }.catch { exception ->
            emit(Result.failure(exception))
        }

    private fun getProductItemsDb(): Flow<Result<List<ProductItem>>> =
        try {
            shoppingCartDao.getAllProductItems().map {
                Result.success(it.map(ShoppingCartEntity::asExternalModel))
            }
        } catch (ex: SQLiteException) {
            flow { emit(Result.failure(ex)) }
        }
}

