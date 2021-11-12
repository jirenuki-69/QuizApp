package com.example.quizapp.db.Settings

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings")
class Settings(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "number_of_questions") var numberOfQuestions: Int,
    @ColumnInfo var difficulty: String,
    @ColumnInfo(name = "hints_enabled") var hintsEnabled: Boolean,
) {
    fun getDifficultyNumber(): Float = when (difficulty) {
        "Fácil" -> 1.0f
        "Medio" -> 2.0f
        "Difícil" -> 3.0f
        else -> 2.0f
    }

    fun setDifficulty(difficulty: Float) {
        this.difficulty = when (difficulty) {
            1.0f -> "Fácil"
            2.0f -> "Medio"
            3.0f -> "Difícil"
            else -> "Medio"
        }
    }

    override fun toString(): String {
        return """
           {
                Id: $id,
                Number of Questions: $numberOfQuestions,
                Difficulty: $difficulty,
                Hints Enabled: $hintsEnabled
           } 
        """.trimIndent()
    }
}