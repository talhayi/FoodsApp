package com.example.foodsapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodsapp.data.model.Cart
import com.example.foodsapp.data.repository.AuthRepository
import com.example.foodsapp.data.repository.FoodsRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val foodsRepository: FoodsRepository,
    private val authRepository: AuthRepository
): ViewModel() {

    val cartList = MutableLiveData<List<Cart>>()
    fun cartList(userName: String) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                cartList.value = foodsRepository.cartList(userName)
            } catch (_: Exception) { }
        }
    }

    fun deleteFoodCart(
        cartFoodId: Int,
        userName: String,
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            foodsRepository.deleteFoodCart(cartFoodId, userName)
            if (cartList.value?.size == 1){
                cartList.value = emptyList()
            }else{
                cartList(userName)
            }
        }
    }

    fun foodTotalPrice(): Int {
        var foodTotalPrice = 0
        cartList.value?.forEach { cartList ->
            foodTotalPrice += cartList.foodPrice!! * cartList.foodOrderQuantity!!
        }
        return foodTotalPrice
    }

    fun cartApprove(){
        for (cart in cartList.value!!) {
            deleteFoodCart(cart.cartFoodId!!, currentUser()?.email!!)
        }
        cartList.value = emptyList()
    }

    fun addFoodCart(
        foodName: String,
        foodImageName: String,
        foodPrice: Int,
        foodOrderQuantity: Int,
        userName: String
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val cartList = foodsRepository.cartList(currentUser()?.email!!)
                if (cartList.isNotEmpty()) {
                    for (cart in cartList) {
                        if (cart.foodName == foodName) {
                            val newQuantity = cart.foodOrderQuantity!! + foodOrderQuantity
                            foodsRepository.addFoodCart(
                                foodName,
                                foodImageName,
                                foodPrice,
                                newQuantity,
                                userName
                            )
                            cartList(currentUser()?.email!!)
                            foodsRepository.deleteFoodCart(cart.cartFoodId!!, currentUser()?.email!!)
                            return@launch // Döngüden çık
                        }
                    }
                }
                // Döngüden çıkmadan buraya gelirse, aynı isimde ürün yok demektir
                foodsRepository.addFoodCart(
                    foodName,
                    foodImageName,
                    foodPrice,
                    foodOrderQuantity,
                    userName
                )
            } catch (_: Exception) {
                foodsRepository.addFoodCart(
                    foodName,
                    foodImageName,
                    foodPrice,
                    foodOrderQuantity,
                    userName
                )
            }
        }
    }

    fun currentUser(): FirebaseUser? {
        return authRepository.currentUser()
    }
}