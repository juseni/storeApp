package com.juannino.data.di

import android.content.Context
import androidx.room.Room
import com.juannino.data.db.ShoppingCartDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author Juan Sebastian Niño - 2023
 */

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    private const val SHOPPING_CART_DATABASE = "shopping_cart_database"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context): ShoppingCartDataBase {
        return synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                ShoppingCartDataBase::class.java,
                SHOPPING_CART_DATABASE
            )
                .fallbackToDestructiveMigration()
                .build()
            instance
        }
    }

    @Singleton
    @Provides
    fun providesShoppingCartDao(db: ShoppingCartDataBase) = db.getShoppingCartDao()
}
