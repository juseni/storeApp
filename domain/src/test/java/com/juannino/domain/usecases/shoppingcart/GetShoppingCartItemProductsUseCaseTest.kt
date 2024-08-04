package com.juannino.domain.usecases.shoppingcart

import com.juannino.data.repositories.ShoppingCartRepository
import com.juannino.domain.utils.productItemsDataMock
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
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
class GetShoppingCartItemProductsUseCaseTest {

    @get:Rule
    val rule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var shoppingCartRepository: ShoppingCartRepository

    private lateinit var getShoppingCartItemProductsUseCase: GetShoppingCartItemProductsUseCase
    private lateinit var initMocks: AutoCloseable

    @Before
    fun setup() {
        initMocks = MockitoAnnotations.openMocks(this)
        getShoppingCartItemProductsUseCase = GetShoppingCartItemProductsUseCase(
            shoppingCartRepository = shoppingCartRepository
        )
    }

    @Test
    fun `get items in shopping cart successful invoke`() = runTest {
        val itemsInBasket = productItemsDataMock.filter { it.itemQuantity > 0 }
        given(shoppingCartRepository.getShoppingCartItemProducts()).willReturn(
            flow { emit(Result.success(itemsInBasket)) }
        )

        getShoppingCartItemProductsUseCase().collect { result ->
            Assert.assertTrue(result.isSuccess)
            val successResult = result.getOrDefault(emptyList())
            Assert.assertEquals(2, successResult.size)
        }
    }

    @Test
    fun `get items in shopping cart fails invoke`() = runTest {
        given(shoppingCartRepository.getShoppingCartItemProducts()).willReturn(
            flow { emit(Result.failure(Exception())) }
        )

        getShoppingCartItemProductsUseCase().collect { result ->
            Assert.assertTrue(result.isFailure)
        }
    }

    @After
    fun tearDown() {
        initMocks.close()
    }
}