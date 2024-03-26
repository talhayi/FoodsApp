package com.example.foodsapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodsapp.data.repository.AuthRepository
import com.example.foodsapp.data.repository.FoodsRepository
import com.example.foodsapp.util.UIState
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    val auth = MutableLiveData<UIState<FirebaseUser?>>()
    fun login (email: String, password: String) {
        CoroutineScope(Dispatchers.Main).launch {
            auth.value = UIState.Loading
            val result = authRepository.login(email, password)
            auth.value = result
        }
    }

    fun register(email: String, password: String) {
        CoroutineScope(Dispatchers.Main).launch {
            auth.value = UIState.Loading
            val result = authRepository.register(email, password)
            auth.value = result
        }
    }

    fun logout() {
        authRepository.logout()
    }

    fun currentUser(): FirebaseUser? {
        return authRepository.currentUser()
    }

}