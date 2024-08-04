package com.juannino.domain.usecases.shoppingcart

import com.juannino.data.repositories.ShoppingCartRepository
import com.juannino.domain.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Juan Sebastian Niño - 2023
 */

@Singleton
class GetItemsQuantityInBasketUseCase @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val shoppingCartRepository: ShoppingCartRepository
) {
    operator fun invoke(): Flow<List<Int>> =
        shoppingCartRepository.getItemsQuantityInCart()
            .flowOn(ioDispatcher)


}