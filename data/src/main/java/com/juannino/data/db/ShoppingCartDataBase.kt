package com.juannino.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.juannino.data.db.dao.ShoppingCartDao
import com.juannino.data.db.entities.ShoppingCartEntity

/**
 * @author Juan Sebastian Niño - 2023
 */

@Database(entities = [ShoppingCartEntity::class], version = 1)
abstract class ShoppingCartDataBase : RoomDatabase() {

    abstract fun getShoppingCartDao(): ShoppingCartDao
}