package com.andreick.shoppinglist.repositories

import androidx.lifecycle.LiveData
import com.andreick.shoppinglist.data.local.ShoppingItem
import com.andreick.shoppinglist.data.remote.responses.ImageResponse
import com.andreick.shoppinglist.other.Resource
import retrofit2.Response

interface ShoppingRepository {

    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    fun observeAllShoppingItems(): LiveData<List<ShoppingItem>>

    fun observeTotalPrice(): LiveData<Float>

    suspend fun searchForImage(imageQuery: String): Resource<ImageResponse>
}