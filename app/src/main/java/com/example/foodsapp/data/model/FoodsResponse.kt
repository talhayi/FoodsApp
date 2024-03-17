package com.example.foodsapp.data.model

import com.google.gson.annotations.SerializedName

data class FoodsResponse(
    @SerializedName("yemekler")
    var foods: List<Foods>,
    var success: String
)
