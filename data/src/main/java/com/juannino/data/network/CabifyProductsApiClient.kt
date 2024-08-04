package com.juannino.data.network

import com.juannino.data.network.models.NetworkProducts
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

/**
 * @author Juan Sebastian Niño - 2023
 */

interface CabifyProductsApiClient {

    companion object {
        private const val PRODUCTS = "Products.json"
    }

    @Headers("Content-Type: application/json")
    @GET(PRODUCTS)
    suspend fun getBreeds(): Response<NetworkProducts>
}