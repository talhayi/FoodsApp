package com.example.foodsapp.data.datasource

import android.util.Log
import com.example.foodsapp.util.UIState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class AuthDataSource(
    private val firebaseAuth: FirebaseAuth
) {
    suspend fun login(email: String, password: String): UIState<FirebaseUser?> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Log.e("deneme","${result.user}")
            UIState.Success(result.user)
        } catch (e: FirebaseAuthWeakPasswordException) {
            UIState.Failure("Zayıf şifre. Lütfen daha güçlü bir şifre seçin.")
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            UIState.Failure("Geçersiz kimlik bilgileri. Lütfen e-posta adresinizi ve şifrenizi kontrol edin.")
        } catch (e: FirebaseAuthUserCollisionException) {
            UIState.Failure("Bu e-postayla kullanıcı zaten mevcut.")

        } catch (e: FirebaseAuthEmailException) {
            UIState.Failure("Geçersiz e-posta. Lütfen geçerli bir e-posta adresi girin.")
        } catch (e: Exception) {
            UIState.Failure("Email ve şifre boş olamaz.")
        }
    }

    suspend fun register(email: String, password: String): UIState<FirebaseUser?> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            UIState.Success(result.user)
        } catch (e: FirebaseAuthWeakPasswordException) {
            UIState.Failure("Zayıf şifre. Lütfen daha güçlü bir şifre seçin.")
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            UIState.Failure("Geçersiz kimlik bilgileri. Lütfen e-posta adresinizi ve şifrenizi kontrol edin.")
        } catch (e: FirebaseAuthUserCollisionException) {
            UIState.Failure("Bu e-postayla kullanıcı zaten mevcut.")

        } catch (e: FirebaseAuthEmailException) {
            UIState.Failure("Geçersiz e-posta. Lütfen geçerli bir e-posta adresi girin.")
        } catch (e: Exception) {
            UIState.Failure("Email ve şifre boş olamaz.")
        }
    }

    fun logout() {
        firebaseAuth.signOut()
    }
    fun currentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

}