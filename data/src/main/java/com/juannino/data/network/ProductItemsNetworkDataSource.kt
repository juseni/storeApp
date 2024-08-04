package com.juannino.data.network

import com.juannino.data.di.CabifyDispatcher.IO
import com.juannino.data.di.Dispatcher
import com.juannino.data.network.models.NetworkProduct
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.ConnectException
import javax.inject.Inject

/**
 * @author Juan Sebastian Niño - 2023
 */

class ProductItemsNetworkDataSource @Inject constructor(
    private val apiClient: CabifyProductsApiClient,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun getAllProductItems(): Result<List<NetworkProduct>> = withContext(ioDispatcher) {
        val response = apiClient.getBreeds()
        if (response.isSuccessful && response.body() != null) {
            Result.success(response.body()!!.products)
        } else {
            Result.failure(ConnectException())
        }
    }
}