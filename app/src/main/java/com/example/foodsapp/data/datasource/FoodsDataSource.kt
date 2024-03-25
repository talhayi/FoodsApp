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

    suspend fun ingredientList(): List<Foods> =
        withContext(Dispatchers.IO){
            val ingredientsList = ArrayList<Foods>()
            val f1 = Foods(1,"Peynir","cheese")
            val f2 = Foods(2,"Soğan","onion")
            val f3 = Foods(3,"Domates","tomato")
            val f4 = Foods(4,"Biber","capsicum")
            val f5 = Foods(5,"Mantar","mushroom")
            ingredientsList.add(f1)
            ingredientsList.add(f2)
            ingredientsList.add(f3)
            ingredientsList.add(f4)
            ingredientsList.add(f5)
            return@withContext ingredientsList
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