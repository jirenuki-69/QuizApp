package com.example.quizapp.db.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scores")
data class Score (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo val difficulty: String,
    @ColumnInfo val date: String,
    @ColumnInfo(name = "number_of_questions") val numberOfQuestions: Int,
    @ColumnInfo(name = "hints_enabled") val hintsEnabled: Boolean,
    @ColumnInfo(name = "total_score") val totalScore: Int,
)