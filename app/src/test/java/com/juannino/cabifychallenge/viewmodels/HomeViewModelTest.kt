package com.juannino.cabifychallenge.viewmodels

import com.juannino.cabifychallenge.utils.MainDispatcherRule
import com.juannino.domain.usecases.shoppingcart.GetItemsQuantityInBasketUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
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

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @get:Rule
    val rule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var  getItemsQuantityInBasketUseCase: GetItemsQuantityInBasketUseCase

    private lateinit var viewModel: HomeViewModel
    private lateinit var initMocks: AutoCloseable
    private var job: Job? = null

    @Before
    fun setup() {
        initMocks = MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `get itemsQuantity when items in basket`() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        val externalCoroutineScope: CoroutineScope = TestScope(testDispatcher)

        given(getItemsQuantityInBasketUseCase()).willReturn(
            flow { emit(listOf(3, 2)) }
        )

        viewModel = HomeViewModel(
            getItemsQuantityInBasketUseCase = getItemsQuantityInBasketUseCase,
            externalScope = externalCoroutineScope
        )

        job = backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.itemsQuantityState.collect { result ->
                Assert.assertEquals(5, result)
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