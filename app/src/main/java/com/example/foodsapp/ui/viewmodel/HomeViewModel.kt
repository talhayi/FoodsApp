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
class HomeViewModel @Inject constructor(
    private val foodsRepository: FoodsRepository
): ViewModel() {

    val foodList = MutableLiveData<List<Foods>>()
    init {
        foodList()
    }

    private fun foodList(){
        CoroutineScope(Dispatchers.Main).launch {
            foodList.value = foodsRepository.foodList()
        }
    }
}