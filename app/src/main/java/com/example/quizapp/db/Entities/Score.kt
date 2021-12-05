package com.example.quizapp.db.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "scores",
    foreignKeys = [
        ForeignKey(
            entity = Profile::class,
            parentColumns = ["id"],
            childColumns = ["profile_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Score(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "profile_id") val profileId: Int,
    @ColumnInfo val difficulty: String,
    @ColumnInfo val date: String,
    @ColumnInfo(name = "number_of_questions") val numberOfQuestions: Int,
    @ColumnInfo(name = "hints_enabled") val hintsEnabled: Boolean,
    @ColumnInfo(name = "total_score") val totalScore: Int,
)