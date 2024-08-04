package com.juannino.discounts.di

import com.juannino.discounts.data.DiscountsRepository
import com.juannino.discounts.data.DiscountsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providesDiscountRepository(): DiscountsRepository = DiscountsRepositoryImpl()
}