package com.example.foodsapp.data.repository

import com.example.foodsapp.data.datasource.AuthDataSource
import com.example.foodsapp.util.UIState
import com.google.firebase.auth.FirebaseUser

class AuthRepository(
    private val authDataSource: AuthDataSource
) {
    suspend fun login(email: String, password: String): UIState<FirebaseUser?> {
        return authDataSource.login(email, password)
    }

    suspend fun register(email: String, password: String): UIState<FirebaseUser?> {
        return authDataSource.register(email, password)
    }

    fun logout(){
        authDataSource.logout()
    }

    fun currentUser(): FirebaseUser? {
        return authDataSource.currentUser()
    }
}