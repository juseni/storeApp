package com.juannino.discounts.data

import com.juannino.discounts.ProductItemDiscounts
import com.juannino.discounts.REDUCE_ONE_WITH_THREE_OR_MORE_PROM
import com.juannino.discounts.TWO_FOR_ONE_PROM
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * @author Juan Sebastian Niño - 2023
 */

class DiscountsRepositoryImpl @Inject constructor() : DiscountsRepository {

    override fun getDiscounts(): Flow<List<ProductItemDiscounts>> =
        // Assuming the data comes from an API
        flow {
            emit(
                listOf(
                    ProductItemDiscounts(
                        code = TWO_FOR_ONE_PROM,
                        productItemCode = listOf("VOUCHER"),
                        messagePromotion = "Buy two items get 1 for free"
                    ),
                    ProductItemDiscounts(
                        code = REDUCE_ONE_WITH_THREE_OR_MORE_PROM,
                        productItemCode = listOf("TSHIRT"),
                        messagePromotion = "Buy three or more items and get 1€ discount in all items"
                    )
                )
            )
        }

    override suspend fun getTotalPriceWithDiscount(
        code: String,
        itemQuantity: Int,
        price: Double
    ): Double =
        // Assuming we call an API to get the real price after discount
        when (code) {
            TWO_FOR_ONE_PROM -> getNewPriceWhenTwoForOneProm(itemQuantity, price)
            REDUCE_ONE_WITH_THREE_OR_MORE_PROM ->
                getNewPriceWhenReduceOneWithThreeProm(itemQuantity, price)
            else -> price
        }


    private fun getNewPriceWhenTwoForOneProm(
        quantityItems: Int,
        price: Double
    ): Double = when {
        quantityItems < 3 -> quantityItems * price
        quantityItems.rem(3) == 0 -> (quantityItems / 3) * 2 * price
        else -> (quantityItems - quantityItems / 3) * price
    }

    private fun getNewPriceWhenReduceOneWithThreeProm(
        quantityItems: Int,
        price: Double
    ): Double =
        if (quantityItems >= 3) {
            quantityItems * (price - 1)
        } else {
            quantityItems * price
        }
}