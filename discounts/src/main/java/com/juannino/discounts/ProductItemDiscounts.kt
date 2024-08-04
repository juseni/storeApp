package com.juannino.discounts

/**
 * @author Juan Sebastian Niño - 2023
 */

data class ProductItemDiscounts(
    val code: String,
    val productItemCode: List<String>,
    val messagePromotion: String
)

const val TWO_FOR_ONE_PROM = "two_for_one_prom"
const val REDUCE_ONE_WITH_THREE_OR_MORE_PROM = "reduce_one_with_three_or_more_prom"
