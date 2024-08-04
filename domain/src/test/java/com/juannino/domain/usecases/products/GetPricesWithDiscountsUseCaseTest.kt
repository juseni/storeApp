package com.juannino.domain.usecases.products

import com.juannino.discounts.data.DiscountsRepository
import com.juannino.domain.models.ProductItemDiscounts
import com.juannino.domain.models.UpdateProductItem
import com.juannino.domain.utils.MainDispatcherRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.After
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
class GetPricesWithDiscountsUseCaseTest {

    @get:Rule
    val rule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var discountsRepository: DiscountsRepository

    private lateinit var getPricesWithDiscountsUseCase: GetPricesWithDiscountsUseCase
    private lateinit var initMocks: AutoCloseable
    private var job: Job? = null

    private val updateProductItem =
        UpdateProductItem(
            code = "TSHIRT",
            price = 20.0,
            quantityItems = 3,
            discounts = ProductItemDiscounts(
                code = "reduce_one_with_three_or_more_prom",
                messageDiscount = " "
            )
        )

    @Before
    fun setup() {
        initMocks = MockitoAnnotations.openMocks(this)
        getPricesWithDiscountsUseCase = GetPricesWithDiscountsUseCase(discountsRepository)
    }

    @Test
    fun `get price for an item with discount`() = runTest {
        job = backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            given(
                discountsRepository.getTotalPriceWithDiscount(
                    updateProductItem.discounts!!.code,
                    updateProductItem.quantityItems,
                    updateProductItem.price
                )
            ).willReturn(57.0)
        }

        val newItem = getPricesWithDiscountsUseCase(updateProductItem)
        assert(newItem.price == 57.0)
    }

    @Test
    fun `get price for an item without discount`() = runTest {
        val updateProductItemWithNoDiscount = updateProductItem.copy(discounts = null)
        val newItem = getPricesWithDiscountsUseCase(updateProductItemWithNoDiscount)
        assert(newItem.price == 60.0)
    }

    @After
    fun tearDown() {
        initMocks.close()
        Dispatchers.resetMain()
        job?.cancel()
    }
}