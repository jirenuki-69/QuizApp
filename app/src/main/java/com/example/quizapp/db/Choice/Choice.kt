package com.example.quizapp.db.Choice

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.example.quizapp.db.Question.Question

@Entity(
    tableName = "choices",
    foreignKeys = [
        ForeignKey(
            entity = Question::class,
            parentColumns = ["id"],
            childColumns = ["question_id"],
            onDelete = CASCADE
        )
    ]
)
data class Choice(
    @PrimaryKey val id: Int,
    @ColumnInfo val text: String,
    @ColumnInfo(name = "is_correct") val isCorrect: Boolean,
    @ColumnInfo(name = "question_id") val questionId: Int
)