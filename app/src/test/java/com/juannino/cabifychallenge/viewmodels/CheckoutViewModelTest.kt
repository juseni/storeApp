package com.juannino.cabifychallenge.viewmodels

import com.juannino.cabifychallenge.ui.model.ProductItemsStateUi
import com.juannino.cabifychallenge.utils.MainDispatcherRule
import com.juannino.cabifychallenge.utils.productItemsMock
import com.juannino.domain.usecases.shoppingcart.GetShoppingCartItemProductsUseCase
import com.juannino.domain.usecases.shoppingcart.MakePaymentUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
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

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CheckoutViewModelTest {

    @get:Rule
    val rule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var getShoppingCartItemProductsUseCase: GetShoppingCartItemProductsUseCase

    @Mock
    private lateinit var makePaymentUseCase: MakePaymentUseCase

    private lateinit var viewModel: CheckoutViewModel
    private lateinit var initMocks: AutoCloseable
    private var job: Job? = null

    @Before
    fun setup() {
        initMocks = MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `test get product items in basket ui state successful`() = runTest {
        val itemsInCart = productItemsMock.filter { it.itemQuantity > 0 }
        given(getShoppingCartItemProductsUseCase()).willReturn(
            flow { emit(Result.success(itemsInCart)) }
        )

        viewModel = CheckoutViewModel(getShoppingCartItemProductsUseCase, makePaymentUseCase)

        // First is Loading
        assertTrue(viewModel.productsStateUi.value is ProductItemsStateUi.Loading)

        // Then collect the data from the useCase
        job = backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.productsStateUi.collect()
        }

        // Expected 1 item with quantity
        val result = viewModel.productsStateUi.value as ProductItemsStateUi.Success
        assertEquals(1, result.productItems.size)
    }

    @Test
    fun `test makePayment success`() = runTest {
        viewModel = CheckoutViewModel(getShoppingCartItemProductsUseCase, makePaymentUseCase)

        job = backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.paymentSuccessfulState.collect { result ->
                assertTrue(result)
            }
        }
    }

    @After
    fun tearDown() {
        initMocks.close()
        Dispatchers.resetMain()
        job?.cancel()
    }
}