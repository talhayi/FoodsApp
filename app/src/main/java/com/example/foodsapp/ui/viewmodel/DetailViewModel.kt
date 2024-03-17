package com.example.foodsapp.ui.viewmodel

import androidx.lifecycle.ViewModel
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
}