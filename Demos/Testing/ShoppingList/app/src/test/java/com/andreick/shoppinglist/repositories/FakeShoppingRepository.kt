package com.andreick.shoppinglist.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.andreick.shoppinglist.data.local.ShoppingItem
import com.andreick.shoppinglist.data.remote.responses.ImageResponse
import com.andreick.shoppinglist.other.Resource

class FakeShoppingRepository : ShoppingRepository {

    var shouldReturnNetworkError = false

    private val shoppingItems = mutableListOf<ShoppingItem>()
    private val observableShoppingItems = MutableLiveData<List<ShoppingItem>>(shoppingItems)
    private val observableTotalPrice = MutableLiveData<Float>()

    private fun refreshLiveData() {
        observableShoppingItems.postValue(shoppingItems)
        observableTotalPrice.postValue(getTotalPrice())
    }

    private fun getTotalPrice() = shoppingItems.sumOf { it.price.toDouble() }.toFloat()

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.add(shoppingItem)
        refreshLiveData()
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.remove(shoppingItem)
        refreshLiveData()
    }

    override fun observeAllShoppingItems(): LiveData<List<ShoppingItem>> = observableShoppingItems

    override fun observeTotalPrice(): LiveData<Float> = observableTotalPrice

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponse> {
        return if (shouldReturnNetworkError) Resource.error("Error", null)
        else Resource.success(ImageResponse(listOf(), 0, 0))
    }
}