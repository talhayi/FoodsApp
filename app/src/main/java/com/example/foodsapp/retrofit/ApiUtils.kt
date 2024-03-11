package com.example.foodsapp.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor


class ApiUtils {
    companion object {
        const val BASE_URL = "http://kasimadalan.pe.hu/"
        private val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        fun getFoodsApi(): FoodsApi {
            return RetrofitClient.getRetrofitClient(BASE_URL)
                .create(FoodsApi::class.java)
        }
    }
}