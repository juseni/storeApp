package com.juannino.domain.usecases.products


import com.juannino.data.repositories.ProductItemsRepository
import com.juannino.discounts.data.DiscountsRepository
import com.juannino.domain.di.IoDispatcher
import com.juannino.domain.models.ProductItem
import com.juannino.domain.models.asDomainModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * @author Juan Sebastian Niño - 2023
 */

class GetProductItemsUseCase @Inject constructor(
   private val productItemsRepository: ProductItemsRepository,
   private val discountsRepository: DiscountsRepository,
   @IoDispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    operator fun invoke(): Flow<Result<List<ProductItem>>> =
        productItemsRepository.getAllProductItems()
            .combine(discountsRepository.getDiscounts()) { productsResult, discounts ->
                if (productsResult.isSuccess) {
                    Result.success(
                        productsResult.getOrDefault(emptyList()).map { productItem ->
                            val discount = discounts.find { productItem.code in it.productItemCode  }
                            productItem.asDomainModel(discount)
                        }
                    )
                } else {
                    Result.failure(productsResult.exceptionOrNull() ?: Throwable())
                }
            }.flowOn(ioDispatcher)
}