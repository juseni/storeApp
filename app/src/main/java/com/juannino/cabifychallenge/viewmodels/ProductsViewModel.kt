package com.juannino.cabifychallenge.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juannino.cabifychallenge.ui.model.ProductItemsStateUi
import com.juannino.domain.models.UpdateProductItem
import com.juannino.domain.usecases.products.GetProductItemsUseCase
import com.juannino.domain.usecases.products.UpdateProductItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Juan Sebastian Niño - 2023
 */

@HiltViewModel
class ProductsViewModel @Inject constructor(
    getProductItemsUseCase: GetProductItemsUseCase,
    private val updateProductItemUseCase: UpdateProductItemUseCase
): ViewModel() {

    val productsStateUi: StateFlow<ProductItemsStateUi> =
        getProductItemsUseCase()
            .map { result ->
                if (result.isSuccess) {
                    val itemProducts = result.getOrDefault(emptyList())
                    ProductItemsStateUi.Success(itemProducts)
                } else {
                    ProductItemsStateUi.Error(result.exceptionOrNull()?.message.orEmpty())
                }
            }.catch {
                emit(ProductItemsStateUi.Error(it.message.orEmpty()))
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
                initialValue = ProductItemsStateUi.Loading
            )

    fun updateItems(updateProductItem: UpdateProductItem) {
        viewModelScope.launch {
            updateProductItemUseCase(updateProductItem)
        }
    }
}