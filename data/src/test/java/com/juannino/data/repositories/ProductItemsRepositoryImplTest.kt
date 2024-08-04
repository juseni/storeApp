package com.juannino.data.repositories

import com.juannino.data.db.dao.ShoppingCartDao
import com.juannino.data.model.ProductItem
import com.juannino.data.network.ProductItemsNetworkDataSource
import com.juannino.data.network.models.NetworkProduct
import com.juannino.data.network.models.asEntity
import com.juannino.data.platform.NetworkHandler
import com.juannino.data.utils.apiProductsMock
import com.juannino.data.utils.productItemsFirstTime
import com.juannino.data.utils.productItemsMock
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
import java.net.ConnectException

/**
 * @author Juan Sebastian Niño - 2023
 */

@RunWith(MockitoJUnitRunner::class)
class ProductItemsRepositoryImplTest {

    @get:Rule
    val rule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var networkHandler: NetworkHandler

    @Mock
    private lateinit var networkDataSource: ProductItemsNetworkDataSource

    @Mock
    private lateinit var shoppingCartDao: ShoppingCartDao

    private lateinit var repository: ProductItemsRepository
    private lateinit var initMocks: AutoCloseable

    @Before
    fun setup() {
        initMocks = MockitoAnnotations.openMocks(this)
        repository = ProductItemsRepositoryImpl(networkHandler, networkDataSource, shoppingCartDao)
    }

    @Test
    fun `test getAllProductItems when device is connected to internet`() = runTest {
        given(networkHandler.isConnected()).willReturn(true)
        given(networkDataSource.getAllProductItems()).willReturn(Result.success(apiProductsMock))
        given(shoppingCartDao.getAllProductItems()).willReturn(
            flow { emit(apiProductsMock.map(NetworkProduct::asEntity)) }
        )

        repository.getAllProductItems().collect {
            assertEquals(Result.success(productItemsFirstTime), it)
        }
    }

    @Test
    fun `test getAllProductItems when device is not connected to internet and no data in DB`() =
        runTest {
            given(networkHandler.isConnected()).willReturn(false)
            given(shoppingCartDao.getAllProductItems()).willReturn(
                flow { emit(emptyList()) }
            )

            repository.getAllProductItems().collect {
                assertEquals(Result.success(emptyList<ProductItem>()), it)
            }
        }

    @Test
    fun `test getAllProductItems when is connected to internet and get an error`() = runTest {
        val connectionException = ConnectException()
        given(networkHandler.isConnected()).willReturn(true)
        given(networkDataSource.getAllProductItems()).willReturn(Result.failure(connectionException))

        repository.getAllProductItems().collect {
            assertTrue(it.isFailure)
        }
    }

    @Test
    fun `test updateProductItem when is successful`() = runTest {
        given(
            shoppingCartDao.updateShoppingItemByCode("VOUCHER", 3, 10.0)
        ).willReturn(1)

        val result = repository.updateProductItem("VOUCHER", 3, 10.0)
        assertEquals(Result.success(true), result)
    }

    @After
    fun tearDown() {
        initMocks.close()
    }
}