package com.example.quizapp.db.Entities

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

    fun getNumberOfChoices(): Int {
        return when (difficulty) {
            "Fácil" -> 2
            "Medio" -> 3
            "Difícil" -> 4
            else -> 3
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