package com.juannino.data.di

import android.content.Context
import com.juannino.data.BuildConfig
import com.juannino.data.network.CabifyProductsApiClient
import com.juannino.data.platform.NetworkHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * @author Juan Sebastian Niño - 2023
 */

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideDogBreedsApiClient(retrofit: Retrofit): CabifyProductsApiClient {
        return retrofit.create(CabifyProductsApiClient::class.java)
    }

    @Singleton
    @Provides
    fun provideNetworkHandler(@ApplicationContext context: Context) =
        NetworkHandler(context)

}