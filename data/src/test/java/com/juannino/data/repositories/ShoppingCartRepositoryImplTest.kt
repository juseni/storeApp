package com.juannino.data.repositories

import android.database.sqlite.SQLiteException
import com.juannino.data.db.dao.ShoppingCartDao
import com.juannino.data.utils.productItemsMock
import com.juannino.data.utils.shoppingCartEntityMock
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule
import org.mockito.kotlin.given

/**
 * @author Juan Sebastian Niño - 2023
 */

@RunWith(MockitoJUnitRunner::class)
class ShoppingCartRepositoryImplTest {

    @get:Rule
    val rule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var shoppingCartDao: ShoppingCartDao

    private lateinit var repository: ShoppingCartRepositoryImpl
    private lateinit var initMocks: AutoCloseable

    @Before
    fun setup() {
        initMocks = MockitoAnnotations.openMocks(this)
        repository = ShoppingCartRepositoryImpl(shoppingCartDao)
    }

    @Test
    fun `test getShoppingCartItemProducts when is successful`() = runTest {
        given(shoppingCartDao.getAllProductItemsToCheckout()).willReturn(
            flow { emit(listOf(shoppingCartEntityMock)) }
        )
        val productsWithItemQuantity = productItemsMock.filter { it.itemQuantity > 0 }

        repository.getShoppingCartItemProducts().collect {
            assertEquals(
                    Result.success(productsWithItemQuantity), it
            )
        }
    }

    @Test
    fun `test getShoppingCartItemProducts when fails`() = runTest {
        given(shoppingCartDao.getAllProductItemsToCheckout()).willThrow(
            SQLiteException()
        )

        repository.getShoppingCartItemProducts().collect {
            it.apply {
                assertTrue(this.isFailure)
            }
        }
    }

    @Test
    fun `test getItemsQuantityInCart when is successful`() = runTest {
        given(shoppingCartDao.getQuantityItemsInCart()).willReturn(
            flow { emit(listOf(1)) }
        )

        repository.getItemsQuantityInCart().collect {
            assertEquals(1, it.sum())
        }
    }

    @Test
    fun `test getItemsQuantityInCart when fails`() = runTest {
        given(shoppingCartDao.getQuantityItemsInCart()).willThrow(
           SQLiteException()
        )

        repository.getItemsQuantityInCart().collect {
            assertTrue(it.first() == Int.MIN_VALUE)
        }
    }

    @After
    fun tearDown() {
        initMocks.close()
    }
}