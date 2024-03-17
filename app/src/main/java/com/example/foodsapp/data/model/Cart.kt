package com.example.foodsapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "cart")
data class Cart(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("sepet_yemek_id")
    var cartFoodId: Int? = null,
    @SerializedName("yemek_adi")
    var foodName: String? = null,
    @SerializedName("yemek_resim_adi")
    var foodImageName: String? = null,
    @SerializedName("yemek_fiyat")
    var foodPrice: Int? = null,
    @SerializedName("yemek_siparis_adet")
    var foodOrderQuantity: Int? = null,
    @SerializedName("kullanici_adi")
    var userName: String? = null,

): Serializable
