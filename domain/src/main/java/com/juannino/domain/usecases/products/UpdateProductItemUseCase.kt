package com.juannino.domain.usecases.products

import com.juannino.data.repositories.ProductItemsRepository
import com.juannino.domain.di.IoDispatcher
import com.juannino.domain.models.UpdateProductItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * @author Juan Sebastian Niño - 2023
 */

class UpdateProductItemUseCase @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val getPricesWithDiscountsUseCase: GetPricesWithDiscountsUseCase,
    private val productItemsRepository: ProductItemsRepository
) {
    suspend operator fun invoke(updateProductItem: UpdateProductItem) = withContext(ioDispatcher) {
        val updateProductItemWithDiscounts =
            getPricesWithDiscountsUseCase(updateProductItem = updateProductItem)

        productItemsRepository.updateProductItem(
            code = updateProductItemWithDiscounts.code,
            itemsQuantity = updateProductItemWithDiscounts.quantityItems,
            newPrice = updateProductItemWithDiscounts.price
        )
    }
}