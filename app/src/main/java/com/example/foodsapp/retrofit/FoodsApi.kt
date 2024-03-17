package com.example.foodsapp.retrofit

import com.example.foodsapp.data.model.FoodsResponse
import retrofit2.http.GET

interface FoodsApi {
    @GET("yemekler/tumYemekleriGetir.php")
    suspend fun foodList(): FoodsResponse
}