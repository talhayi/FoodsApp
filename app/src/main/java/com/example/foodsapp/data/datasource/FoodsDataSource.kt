package com.example.foodsapp.data.datasource

import com.example.foodsapp.retrofit.FoodsApi
import com.example.foodsapp.room.FoodsDao

class FoodsDataSource(
    private val foodsDao: FoodsDao,
    private val foodsApi: FoodsApi
) {
}