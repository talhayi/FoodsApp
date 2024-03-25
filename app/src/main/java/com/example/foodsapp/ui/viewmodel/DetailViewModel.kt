package com.example.foodsapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodsapp.data.model.Foods
import com.example.foodsapp.data.repository.FoodsRepository
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

    fun addFoodCart( foodName: String,
                     foodImageName: String,
                     foodPrice: Int,
                     foodOrderQuantity: Int,
                     userName: String) {
        CoroutineScope(Dispatchers.Main).launch {
            foodsRepository.addFoodCart(
                foodName,
                foodImageName,
                foodPrice,
                foodOrderQuantity,
                userName
            )
        }
    }

    private fun ingredientList() {
        CoroutineScope(Dispatchers.Main).launch {
            ingredientList.value = foodsRepository.ingredientList()
        }
    }
}