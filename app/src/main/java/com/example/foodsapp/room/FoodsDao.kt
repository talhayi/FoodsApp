package com.example.foodsapp.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.foodsapp.data.model.Foods

@Dao
interface FoodsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(foods: Foods): Long

    @Query("SELECT * FROM foods")
    fun getFavoriteList(): LiveData<List<Foods>>

    @Delete
    suspend fun deleteFavorite(foods: Foods)
}