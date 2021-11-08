package com.example.quizapp.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings")
data class Settings(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "number_of_questions") val numberOfQuestions: Int,
    @ColumnInfo val difficulty: String,
    @ColumnInfo(name = "hints_enabled") val hintsEnabled: Boolean,
)