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
class GetItemsQuantityInBasketUseCaseTest {

    @get:Rule
    val rule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var shoppingCartRepository: ShoppingCartRepository

    private lateinit var getItemsQuantityInBasketUseCase: GetItemsQuantityInBasketUseCase
    private lateinit var initMocks: AutoCloseable

    @Before
    fun setup() {
        initMocks = MockitoAnnotations.openMocks(this)
        getItemsQuantityInBasketUseCase =
            GetItemsQuantityInBasketUseCase(shoppingCartRepository = shoppingCartRepository)
    }

    @Test
    fun `get quantity item in basket`() = runTest {
        val itemsWithQuantity =
            productItemsDataMock.filter { it.itemQuantity > 0 }.map { it.itemQuantity }

        given(shoppingCartRepository.getItemsQuantityInCart()).willReturn(
            flow { emit(listOf(3, 2)) }
        )

        getItemsQuantityInBasketUseCase().collect { result ->
            Assert.assertEquals(itemsWithQuantity, result)
        }
    }

    @After
    fun tearDown() {
        initMocks.close()
    }

}