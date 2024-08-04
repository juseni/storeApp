package com.juannino.cabifychallenge.ui.model

import com.juannino.domain.models.ProductItem

/**
 * @author Juan Sebastian Niño - 2023
 */

sealed interface ProductItemsStateUi {

    data object Loading : ProductItemsStateUi

    data class Success(val productItems: List<ProductItem>) : ProductItemsStateUi

    data class Error(val errorMessage: String) : ProductItemsStateUi

}