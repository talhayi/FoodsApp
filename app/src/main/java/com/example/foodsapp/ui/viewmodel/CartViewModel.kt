package com.example.foodsapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodsapp.data.model.Cart
import com.example.foodsapp.data.repository.FoodsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val foodsRepository: FoodsRepository
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
}