package com.juannino.domain.usecases.shoppingcart

import com.juannino.data.repositories.ShoppingCartRepository
import com.juannino.domain.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * @author Juan Sebastian Niño - 2023
 */

class MakePaymentUseCase @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val shoppingCartRepository: ShoppingCartRepository
) {
    suspend operator fun invoke(): Boolean = withContext(ioDispatcher) {
        shoppingCartRepository.makeShoppingCartPayment()
    }
}