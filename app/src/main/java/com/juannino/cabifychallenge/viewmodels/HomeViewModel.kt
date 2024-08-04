package com.juannino.cabifychallenge.viewmodels

import androidx.lifecycle.ViewModel
import com.juannino.domain.di.ApplicationScope
import com.juannino.domain.usecases.shoppingcart.GetItemsQuantityInBasketUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

/**
 * @author Juan Sebastian Niño - 2023
 */

@HiltViewModel
class HomeViewModel @Inject constructor(
    getItemsQuantityInBasketUseCase: GetItemsQuantityInBasketUseCase,
    @ApplicationScope private val externalScope: CoroutineScope
): ViewModel() {

    val itemsQuantityState: SharedFlow<Int> =
        getItemsQuantityInBasketUseCase()
            .map { it.sum() }
            .catch {
                emit(Int.MIN_VALUE)
            }.shareIn(
                scope = externalScope,
                started = SharingStarted.Eagerly,
                replay = 10
            )
}