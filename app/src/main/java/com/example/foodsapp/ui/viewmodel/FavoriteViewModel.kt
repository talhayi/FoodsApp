package com.example.foodsapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodsapp.data.model.Foods
import com.example.foodsapp.data.repository.FoodsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val foodsRepository: FoodsRepository
): ViewModel() {

    fun addFavorite (foods: Foods){
        CoroutineScope(Dispatchers.Main).launch {
            foodsRepository.addFavorite(foods)
        }
    }

    fun getFavoriteList() =
        foodsRepository.getFavoriteList()

    fun deleteFavorite(foods: Foods){
        CoroutineScope(Dispatchers.Main).launch {
            foodsRepository.deleteFavorite(foods)
        }
    }
}