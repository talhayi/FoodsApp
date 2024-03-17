package com.example.foodsapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "foods")
data class Foods(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("yemek_id")
    var foodId: Int? = null,
    @SerializedName("yemek_adi")
    var foodName: String? = null,
    @SerializedName("yemek_resim_adi")
    var foodImageName: String? = null,
    @SerializedName("yemek_fiyat")
    var foodPrice: Int? = null,
): Serializable
