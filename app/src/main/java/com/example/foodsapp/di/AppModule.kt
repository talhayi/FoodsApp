package com.example.foodsapp.di

import android.content.Context
import androidx.room.Room
import com.example.foodsapp.data.datasource.AuthDataSource
import com.example.foodsapp.data.datasource.FoodsDataSource
import com.example.foodsapp.data.repository.AuthRepository
import com.example.foodsapp.data.repository.FoodsRepository
import com.example.foodsapp.retrofit.ApiUtils
import com.example.foodsapp.retrofit.FoodsApi
import com.example.foodsapp.room.FoodsDao
import com.example.foodsapp.room.FoodsDatabase
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFoodsDataSource(
        foodsDao: FoodsDao,
        foodsApi: FoodsApi,
    ): FoodsDataSource {
        return FoodsDataSource(foodsDao, foodsApi)
    }

    @Provides
    @Singleton
    fun provideFoodsRepository(foodsDataSource: FoodsDataSource): FoodsRepository {
        return FoodsRepository(foodsDataSource)
    }

    @Provides
    @Singleton
    fun provideFoodsApi(): FoodsApi{
        return ApiUtils.getFoodsApi()
    }

    @Provides
    @Singleton
    fun provideFoodsDao(@ApplicationContext context: Context): FoodsDao{
        val db = Room.databaseBuilder(context, FoodsDatabase::class.java,"foods.sqlite").build()
        return db.getFoodsDao()
    }


    @Provides
    @Singleton
    fun provideAuthDataSource(
        firebaseAuth: FirebaseAuth
    ): AuthDataSource {
        return AuthDataSource(firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(authDataSource: AuthDataSource): AuthRepository {
        return AuthRepository(authDataSource)
    }

    @Provides
    @Singleton
    fun provideFirebaseAuthInstance(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }
}