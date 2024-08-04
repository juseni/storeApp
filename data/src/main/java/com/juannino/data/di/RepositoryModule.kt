package com.juannino.data.di

import com.juannino.data.db.dao.ShoppingCartDao
import com.juannino.data.network.ProductItemsNetworkDataSource
import com.juannino.data.platform.NetworkHandler
import com.juannino.data.repositories.ProductItemsRepository
import com.juannino.data.repositories.ProductItemsRepositoryImpl
import com.juannino.data.repositories.ShoppingCartRepository
import com.juannino.data.repositories.ShoppingCartRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author Juan Sebastian Niño - 2023
 */

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun bindsProductItemsRepository(
        networkHandler: NetworkHandler,
        networkDataSource: ProductItemsNetworkDataSource,
        shoppingCartDao: ShoppingCartDao
    ): ProductItemsRepository =
        ProductItemsRepositoryImpl(networkHandler, networkDataSource, shoppingCartDao)

    @Singleton
    @Provides
    fun bindsShoppingCartRepository(
        shoppingCartDao: ShoppingCartDao
    ): ShoppingCartRepository =
        ShoppingCartRepositoryImpl(shoppingCartDao = shoppingCartDao)

}