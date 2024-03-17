package com.example.foodsapp.data.datasource

import com.example.foodsapp.data.model.Foods
import com.example.foodsapp.retrofit.FoodsApi
import com.example.foodsapp.room.FoodsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FoodsDataSource(
    private val foodsDao: FoodsDao,
    private val foodsApi: FoodsApi
) {
    suspend fun foodList(): List<Foods> =
        withContext(Dispatchers.IO){
            return@withContext foodsApi.foodList().foods
        }
}