package com.andreick.shoppinglist.data.local

import androidx.room.Database

@Database(entities = [ShoppingItem::class], version = 1)
abstract class ShoppingItemDatabase {

    abstract fun shoppingDao(): ShoppingDao
}