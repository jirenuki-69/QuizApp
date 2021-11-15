package com.example.quizapp.db.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE

@Entity(
    tableName = "game_choices",
    foreignKeys = [
        ForeignKey(
            entity = Choice::class,
            parentColumns = ["id"],
            childColumns = ["choice_id"],
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = GameQuestion::class,
            parentColumns = ["id"],
            childColumns = ["game_question_id"],
            onDelete = CASCADE
        )
    ],
    primaryKeys = ["game_question_id", "choice_id"]
)
data class GameChoices(
    @ColumnInfo(name = "game_question_id") val gameQuestionId: Int,
    @ColumnInfo(name = "choice_id") val choiceId: Int,
    @ColumnInfo(name = "is_answered") var isAnswered: Boolean,
    @ColumnInfo(name = "position_number") var positionNumber: Int
)