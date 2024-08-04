package com.juannino.cabifychallenge.viewmodels

import com.juannino.cabifychallenge.ui.model.ProductItemsStateUi
import com.juannino.cabifychallenge.utils.MainDispatcherRule
import com.juannino.cabifychallenge.utils.productItemsMock
import com.juannino.cabifychallenge.utils.updatedProductsItemMock
import com.juannino.domain.models.ProductItemDiscounts
import com.juannino.domain.models.UpdateProductItem
import com.juannino.domain.usecases.products.GetProductItemsUseCase
import com.juannino.domain.usecases.products.UpdateProductItemUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
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
class ProductsViewModelTest {

    @get:Rule
    val rule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var getProductItemsUseCase: GetProductItemsUseCase

    @Mock
    private lateinit var updateProductItemUseCase: UpdateProductItemUseCase

    private lateinit var viewModel: ProductsViewModel
    private lateinit var initMocks: AutoCloseable
    private var job: Job? = null

    @Before
    fun setup() {
        initMocks = MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `get products when productsStateUi is success`() = runTest {
        given(getProductItemsUseCase()).willReturn(
            flow { emit(Result.success(productItemsMock)) }
        )

        viewModel = ProductsViewModel(getProductItemsUseCase, updateProductItemUseCase)

        // First is Loading
        assertTrue(viewModel.productsStateUi.value is ProductItemsStateUi.Loading)

        // Then collect the data from the useCase
        job = backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.productsStateUi.collect()
        }

        // Expected 3 items
        val result = viewModel.productsStateUi.value as ProductItemsStateUi.Success
        assertEquals(3, result.productItems.size)
    }

    @Test
    fun `get products when productsStateUi is fail`() = runTest {
        given(getProductItemsUseCase()).willReturn(
            flow { emit(Result.failure(Exception())) }
        )

        viewModel = ProductsViewModel(getProductItemsUseCase, updateProductItemUseCase)

        // First is Loading
        assertTrue(viewModel.productsStateUi.value is ProductItemsStateUi.Loading)

        // Then collect the data from the useCase
        job = backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.productsStateUi.collect()
        }

        // Expected Error
        assertTrue(viewModel.productsStateUi.value is ProductItemsStateUi.Error)
    }

    @Test
    fun `updateItems and should get the update`() = runTest {
        val testResult = mutableListOf<ProductItemsStateUi>()
        val updateProductItem =
            UpdateProductItem(
                code = "TSHIRT",
                price = 20.0,
                quantityItems = 3,
                discounts = ProductItemDiscounts(
                    code = "reduce_one_with_three_or_more_prom",
                    messageDiscount = " "
                )
            )
        given(getProductItemsUseCase()).willReturn(
            flow { emit(Result.success(productItemsMock)) }
        )

        viewModel = ProductsViewModel(getProductItemsUseCase, updateProductItemUseCase)
        given(updateProductItemUseCase(updateProductItem)).willReturn(true)

        job = backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.productsStateUi.toList(testResult)
        }

        viewModel.updateItems(updateProductItem)
        // Drop 0, belongs to Loading state
        testResult.removeFirst()
        updateProductItemCall(testResult)
    }

    private fun updateProductItemCall(firsResultList: List<ProductItemsStateUi>) = runTest {
        val testResult = mutableListOf<ProductItemsStateUi>()
        testResult.addAll(firsResultList)
        given(getProductItemsUseCase()).willReturn(
            flow { emit(Result.success(updatedProductsItemMock)) }
        )
        viewModel = ProductsViewModel(getProductItemsUseCase, updateProductItemUseCase)
        job = backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.productsStateUi.toList(testResult)
        }
        // Drop Loading state
        testResult.remove(ProductItemsStateUi.Loading)

        assertTrue(testResult.size == 2)
        val productItemsList = testResult.map { (it as ProductItemsStateUi.Success).productItems }

        // validate first item, and second item are updated
        assertEquals(productItemsMock, productItemsList.first())
        assertEquals(updatedProductsItemMock, productItemsList[1])
    }

    @After
    fun tearDown() {
        initMocks.close()
        Dispatchers.resetMain()
        job?.cancel()
    }
}