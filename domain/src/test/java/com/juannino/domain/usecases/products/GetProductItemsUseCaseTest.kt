package com.juannino.domain.usecases.products

import com.juannino.data.repositories.ProductItemsRepository
import com.juannino.discounts.data.DiscountsRepository
import com.juannino.domain.utils.productItemsDataMock
import com.juannino.domain.utils.productItemsDiscountMock
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
class GetProductItemsUseCaseTest {

    @get:Rule
    val rule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var productItemsRepository: ProductItemsRepository

    @Mock
    private lateinit var discountsRepository: DiscountsRepository

    private lateinit var getProductItemsUseCase: GetProductItemsUseCase
    private lateinit var initMocks: AutoCloseable

    @Before
    fun setup() {
        initMocks = MockitoAnnotations.openMocks(this)
        getProductItemsUseCase = GetProductItemsUseCase(
            productItemsRepository = productItemsRepository,
            discountsRepository = discountsRepository
        )
    }

    @Test
    fun `when use case invoke is successful`() = runTest {
        given(productItemsRepository.getAllProductItems()).willReturn(
            flow { emit(Result.success(productItemsDataMock)) }
        )
        given(discountsRepository.getDiscounts()).willReturn(
            flow { emit(productItemsDiscountMock) }
        )

        getProductItemsUseCase().collect { response ->
            assertTrue(response.isSuccess)
            val result = response.getOrDefault(emptyList())
            assert(result.size == 3)
            // Check there are two productItems with discount
            assertEquals(2, result.filter { it.discount != null }.size)
        }
    }

    @Test
    fun `when use case invoke fails`() = runTest {
        val exception = Exception()
        given(productItemsRepository.getAllProductItems()).willReturn(
            flow { emit(Result.failure(exception)) }
        )
        given(discountsRepository.getDiscounts()).willReturn(
            flow { emit(productItemsDiscountMock) }
        )

        getProductItemsUseCase().collect { response ->
            assertTrue(response.isFailure)
        }
    }

    @After
    fun tearDown() {
        initMocks.close()
    }
}