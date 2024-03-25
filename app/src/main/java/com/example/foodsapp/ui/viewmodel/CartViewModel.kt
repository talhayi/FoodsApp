package com.example.foodsapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodsapp.data.model.Cart
import com.example.foodsapp.data.repository.FoodsRepository
import com.example.foodsapp.util.USERNAME
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

    fun foodTotalPrice(): Int {
        var foodTotalPrice = 0
        cartList.value?.forEach { cartList ->
            foodTotalPrice += cartList.foodPrice!! * cartList.foodOrderQuantity!!
        }
        return foodTotalPrice
    }

    fun cartApprove(){
        for (cart in cartList.value!!) {
            deleteFoodCart(cart.cartFoodId!!, USERNAME)
        }
        cartList.value = emptyList()
    }

    fun consolidateCartList(cartList: List<Cart>): List<Cart> {
        val consolidatedList = mutableListOf<Cart>()
        val cartMap = mutableMapOf<String, Int>()

        try {
            // Aynı isme sahip yemekleri topla
            for (cart in cartList) {
                val quantity = cartMap[cart.foodName]
                if (quantity != null) {
                    cartMap[cart.foodName!!] = quantity + cart.foodOrderQuantity!!
                } else {
                    cartMap[cart.foodName!!] = cart.foodOrderQuantity!!
                }
            }

            // Yeni listeyi oluştur
            for ((foodName, quantity) in cartMap) {
                val cart = Cart(foodName = foodName, foodOrderQuantity = quantity)
                consolidatedList.add(cart)
            }
        }catch (_: Exception){}
        return consolidatedList
    }

/*    fun consolidateCartList(cartList: List<Cart>): List<Cart> {
        val consolidatedList = mutableListOf<Cart>()
        val cartMap = mutableMapOf<String, Int>()

        // Aynı isme sahip yemekleri topla
        for (cart in cartList) {
            val quantity = cartMap[cart.foodName]
            if (quantity != null) {
                cartMap[cart.foodName!!] = quantity + cart.foodOrderQuantity!!
            } else {
                cartMap[cart.foodName!!] = cart.foodOrderQuantity!!
            }
        }

        // Yeni listeyi oluştur
        for ((foodName, quantity) in cartMap) {
            val cart = Cart(null, foodName, "", 0, quantity, USERNAME)
            consolidatedList.add(cart)
        }
        return consolidatedList
    }

    fun createConsolidatedList(): Map<String, Int> {
        val cartMap = mutableMapOf<String, Int>()
        cartList.value?.forEach { cart ->
            val foodName = cart.foodName
            val quantity = cart.foodOrderQuantity ?: 0
            if (foodName != null) {
                cartMap[foodName] = cartMap.getOrDefault(foodName, 0) + quantity
            }
        }
        return cartMap
    }*/
}