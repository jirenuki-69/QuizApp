package com.example.quizapp.db.Entities

import androidx.room.*
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
    ]
)
data class GameQuestion(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "game_id") val gameId: Int,
    @ColumnInfo(name = "question_id") val questionId: Int,
    @ColumnInfo(name = "question_number") val questionNumber: Int,
    @ColumnInfo(name = "is_answered") var isAnswered: Boolean,
    @ColumnInfo(name = "is_answered_by_hint") var isAnsweredByHint: Boolean,
    @ColumnInfo(name = "hints_used") var hintsUsed: Boolean,
    @ColumnInfo(name = "is_correct") var isCorrect: Boolean,
)