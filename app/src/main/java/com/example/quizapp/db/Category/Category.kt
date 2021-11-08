package com.example.quizapp.db.Category

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey val id: Int,
    @ColumnInfo val name: String
)