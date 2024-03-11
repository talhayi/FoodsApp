package com.example.foodsapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.foodsapp.data.model.Foods

@Database(entities = [Foods::class], version = 1)
abstract class FoodsDatabase: RoomDatabase() {
    abstract fun getFoodsDao(): FoodsDao

}