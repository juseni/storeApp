package com.juannino.domain.usecases.shoppingcart

import com.juannino.data.repositories.ShoppingCartRepository
import com.juannino.domain.di.IoDispatcher
import com.juannino.domain.models.ProductItem
import com.juannino.domain.models.asDomainModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import com.juannino.data.model.ProductItem as DataProductItem

/**
 * @author Juan Sebastian Niño - 2023
 */

class GetShoppingCartItemProductsUseCase @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val shoppingCartRepository: ShoppingCartRepository
) {
    operator fun invoke(): Flow<Result<List<ProductItem>>> =
        shoppingCartRepository.getShoppingCartItemProducts()
            .map { result ->
                if (result.isSuccess) {
                    Result.success(
                        result.getOrDefault(emptyList()).map(DataProductItem::asDomainModel)
                    )
                } else {
                    Result.failure(result.exceptionOrNull() ?: Throwable())
                }
            }.flowOn(ioDispatcher)
}