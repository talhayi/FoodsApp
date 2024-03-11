package com.example.foodsapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.foodsapp.data.repository.FoodsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val foodsRepository: FoodsRepository
): ViewModel() {
}