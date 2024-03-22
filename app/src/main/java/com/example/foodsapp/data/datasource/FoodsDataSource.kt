package com.example.foodsapp.data.datasource

import com.example.foodsapp.data.model.CRUDResponse
import com.example.foodsapp.data.model.Cart
import com.example.foodsapp.data.model.CartResponse
import com.example.foodsapp.data.model.Foods
import com.example.foodsapp.retrofit.FoodsApi
import com.example.foodsapp.room.FoodsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.http.Field

class FoodsDataSource(
    private val foodsDao: FoodsDao,
    private val foodsApi: FoodsApi
) {
    suspend fun foodList(): List<Foods> =
        withContext(Dispatchers.IO) {
            return@withContext foodsApi.foodList().foods
        }

    suspend fun addFoodCart(
        foodName: String,
        foodImageName: String,
        foodPrice: Int,
        foodOrderQuantity: Int,
        userName: String
    ): CRUDResponse =
        foodsApi.addFoodCart(foodName, foodImageName, foodPrice, foodOrderQuantity, userName)

    suspend fun cartList(userName: String): List<Cart> =
        withContext(Dispatchers.IO) {
            return@withContext foodsApi.cartList(userName).cart
        }

    suspend fun deleteFoodCart(
       cartFoodId: Int,
        userName: String,
    ): CRUDResponse =
        foodsApi.deleteFoodCart(cartFoodId, userName)

    suspend fun addFavorite(foods: Foods) {
            foodsDao.insert(foods)
    }

    fun getFavoriteList() = foodsDao.getFavoriteList()

    suspend fun deleteFavorite(foods: Foods){
        foodsDao.deleteFavorite(foods)
    }
}