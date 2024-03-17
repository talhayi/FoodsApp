package com.example.foodsapp.data.repository

import com.example.foodsapp.data.datasource.FoodsDataSource
import com.example.foodsapp.data.model.Foods

class FoodsRepository(
    private val foodsDataSource: FoodsDataSource
) {
    suspend fun foodList(): List<Foods> =
        foodsDataSource.foodList()
}