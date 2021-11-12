package com.example.quizapp.db.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE

@Entity(
    tableName = "game_questions",
    foreignKeys = [
        ForeignKey(
            entity = Game::class,
            parentColumns = ["id"],
            childColumns = ["game_id"],
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = Question::class,
            parentColumns = ["id"],
            childColumns = ["question_id"],
            onDelete = CASCADE
        )
    ],
    primaryKeys = ["game_id", "question_id"]
)
data class GameQuestion(
    @ColumnInfo(name = "game_id") val gameId: Int,
    @ColumnInfo(name = "question_id") val questionId: Int,
    @ColumnInfo(name = "question_number") val questionNumber: Int,
    @ColumnInfo(name = "is_answered") val isAnswered: Boolean,
    @ColumnInfo(name = "is_answered_by_hint") val isAnsweredByHint: Boolean,
    @ColumnInfo(name = "hints_used") val hintsUsed: Boolean,
    @ColumnInfo(name = "is_correct") val isCorrect: Boolean,
)