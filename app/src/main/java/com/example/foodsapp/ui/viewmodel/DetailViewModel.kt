package com.example.foodsapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodsapp.data.model.Foods
import com.example.foodsapp.data.repository.FoodsRepository
import com.example.foodsapp.util.USERNAME
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val foodsRepository: FoodsRepository
): ViewModel() {

    val ingredientList = MutableLiveData<List<Foods>>()
    init {
        ingredientList()
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
                val cartList = foodsRepository.cartList(USERNAME)
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
                            foodsRepository.deleteFoodCart(cart.cartFoodId!!, USERNAME)
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

    private fun ingredientList() {
        CoroutineScope(Dispatchers.Main).launch {
            ingredientList.value = foodsRepository.ingredientList()
        }
    }
}