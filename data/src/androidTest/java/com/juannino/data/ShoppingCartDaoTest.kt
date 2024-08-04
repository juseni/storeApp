package com.juannino.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.juannino.data.db.ShoppingCartDataBase
import com.juannino.data.db.dao.ShoppingCartDao
import com.juannino.data.utils.MainDispatcherRule
import com.juannino.data.utils.shoppingCartEntityMock
import com.juannino.data.utils.shoppingCartEntityMock2
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * @author Juan Sebastian Niño - 2023
 */

@RunWith(AndroidJUnit4::class)
class ShoppingCartDaoTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var itemDao: ShoppingCartDao
    private lateinit var itemDb: ShoppingCartDataBase

    @Before
    fun create() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        itemDb = Room
            .inMemoryDatabaseBuilder(context, ShoppingCartDataBase::class.java)
            .build()
        itemDao = itemDb.getShoppingCartDao()
    }


    @Test
    fun insertAllProductItems_shouldReturn_theItems_inFlow() = runTest {
        itemDao.insertAllProductItems(listOf(shoppingCartEntityMock, shoppingCartEntityMock2))

        itemDao.getAllProductItems().test {
            val productItems = awaitItem()
            assert(productItems.contains(shoppingCartEntityMock))
            assert(productItems.contains(shoppingCartEntityMock2))
            cancel()
        }
    }

    @Test
    fun insertAllProductItemsInBasket_shouldReturn_theItemsInBasket_inFlow() = runTest {
        itemDao.insertAllProductItems(listOf(shoppingCartEntityMock, shoppingCartEntityMock2))

        itemDao.getAllProductItemsToCheckout().test {
            val productItems = awaitItem()
            assert(productItems.contains(shoppingCartEntityMock))
            assertFalse(productItems.contains(shoppingCartEntityMock2))
            cancel()
        }
    }

    @Test
    fun updateProductItem_quantity_and_totalPrice_shouldReturn_one_row_updated() = runTest {
        itemDao.insertAllProductItems(listOf(shoppingCartEntityMock, shoppingCartEntityMock2))

        val result = itemDao.updateShoppingItemByCode("TSHIRT", 3, 57.0)
        assertTrue(result == 1)
    }

    @After
    fun cleanup() {
        itemDb.close()
        Dispatchers.resetMain()
    }

}