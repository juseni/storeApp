package com.juannino.domain.usecases.products

import com.juannino.discounts.data.DiscountsRepository
import com.juannino.domain.models.UpdateProductItem
import javax.inject.Inject

/**
 * @author Juan Sebastian Niño - 2023
 */

class GetPricesWithDiscountsUseCase @Inject constructor(
    private val discountsRepository: DiscountsRepository
) {
    suspend operator fun invoke(updateProductItem: UpdateProductItem): UpdateProductItem {
        val newPrice = if (updateProductItem.discounts != null) {
            discountsRepository.getTotalPriceWithDiscount(
                code = updateProductItem.discounts.code,
                itemQuantity = updateProductItem.quantityItems,
                price = updateProductItem.price
            )
        } else {
            updateProductItem.quantityItems * updateProductItem.price
        }
        return updateProductItem.copy(price = newPrice)
    }
}