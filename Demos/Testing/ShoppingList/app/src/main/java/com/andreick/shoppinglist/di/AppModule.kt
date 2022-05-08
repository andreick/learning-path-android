package com.andreick.shoppinglist.di

import android.content.Context
import androidx.room.Room
import com.andreick.shoppinglist.data.local.ShoppingItemDatabase
import com.andreick.shoppinglist.data.remote.PixabayApi
import com.andreick.shoppinglist.other.Constants.PIXABAY_BASE_URL
import com.andreick.shoppinglist.other.Constants.SHOPPING_ITEM_DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideShoppingItemDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, ShoppingItemDatabase::class.java, SHOPPING_ITEM_DB_NAME).build()

    @Provides
    fun provideShoppingDao(database: ShoppingItemDatabase) = database.shoppingDao()

    @Provides
    @Singleton
    fun providePixabayApi() = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(PIXABAY_BASE_URL)
        .build()
        .create(PixabayApi::class.java)
}