package com.example.foodsapp.retrofit

import com.example.foodsapp.data.model.CRUDResponse
import com.example.foodsapp.data.model.CartResponse
import com.example.foodsapp.data.model.FoodsResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface FoodsApi {
    @GET("yemekler/tumYemekleriGetir.php")
    suspend fun foodList(): FoodsResponse

    @POST("yemekler/sepeteYemekEkle.php")
    @FormUrlEncoded
    suspend fun addFoodCart(
        @Field("yemek_adi") foodName: String,
        @Field("yemek_resim_adi") foodImageName: String,
        @Field("yemek_fiyat") foodPrice: Int,
        @Field("yemek_siparis_adet") foodOrderQuantity: Int,
        @Field("kullanici_adi") userName: String,
    ): CRUDResponse

    @POST("yemekler/sepettekiYemekleriGetir.php")
    @FormUrlEncoded
    suspend fun cartList(
        @Field("kullanici_adi") userName: String,
    ): CartResponse

    @POST("yemekler/sepettenYemekSil.php")
    @FormUrlEncoded
    suspend fun deleteFoodCart(
        @Field("sepet_yemek_id") cartFoodId: Int,
        @Field("kullanici_adi") userName: String,
    ): CRUDResponse
}