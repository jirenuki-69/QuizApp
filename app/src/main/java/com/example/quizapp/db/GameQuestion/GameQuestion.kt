package com.example.quizapp.db.GameQuestion

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GameQuestion(
    @PrimaryKey val game_id:Int,
    @PrimaryKey val question_id:Int,
    @ColumnInfo(name = "question_number") val questionNumber: Int,
    @ColumnInfo(name = "is_answered") val isAnswered: Boolean,
    @ColumnInfo(name = "is_answered_by_hint") val isAnsweredByHint: Boolean,
    @ColumnInfo(name = "hints_used") val hintsUsed: Boolean,
    @ColumnInfo(name = "is_correct") val isCorrect: Boolean,
)