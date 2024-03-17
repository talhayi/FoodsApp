package com.example.foodsapp.data.repository

import com.example.foodsapp.data.datasource.FoodsDataSource
import com.example.foodsapp.data.model.CRUDResponse
import com.example.foodsapp.data.model.Cart
import com.example.foodsapp.data.model.Foods
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FoodsRepository(
    private val foodsDataSource: FoodsDataSource
) {
    suspend fun foodList(): List<Foods> =
        foodsDataSource.foodList()

    suspend fun addFoodCart(
        foodName: String,
        foodImageName: String,
        foodPrice: Int,
        foodOrderQuantity: Int,
        userName: String
    ): CRUDResponse =
        foodsDataSource.addFoodCart(foodName, foodImageName, foodPrice, foodOrderQuantity, userName)

    suspend fun cartList(userName: String): List<Cart> =
       foodsDataSource.cartList(userName)
}