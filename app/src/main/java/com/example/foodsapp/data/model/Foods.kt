package com.example.foodsapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "foods")
data class Foods(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int
)
