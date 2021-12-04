package com.example.quizapp.db.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "games",
    foreignKeys = [
        ForeignKey(
            entity = Profile::class,
            parentColumns = ["id"],
            childColumns = ["profile_id"],
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = Settings::class,
            parentColumns = ["id"],
            childColumns = ["settings_id"],
            onDelete = CASCADE,
            onUpdate = CASCADE
        )
    ]
)
data class Game(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "profile_id") val profileId: Int,
    @ColumnInfo(name = "settings_id") val settingsId: Int,
    @ColumnInfo var finished: Int,
    @ColumnInfo(name = "current_score") var currentScore: Int,
    @ColumnInfo(name = "current_question") var currentQuestion: Int,
) {
    @ColumnInfo(name = "number_of_hints_available")
    var numberOfHintsAvailable: Int = 0
    @ColumnInfo(name = "questions_answered")
    var questionsAnswered = 0
    @ColumnInfo(name = "correct_answers_without_hint")
    var correctAnswersWithoutHint = 0
    @ColumnInfo(name = "number_of_hints_used")
    var numberOfHintsUsed = 0
    @ColumnInfo(name = "number_of_correct_answers")
    var numberOfCorrectAnswers = 0

    fun setHints (difficulty: String) {
        numberOfHintsAvailable = when (difficulty) {
            "Fácil" -> 5
            "Medio" -> 3
            "Difícil" -> 1
            else -> 3
        }
    }

    fun previousQuestion(numberOfQuestions: Int): Int {
        val size = numberOfQuestions - 1
        currentQuestion = size - (if (currentQuestion > 0) numberOfQuestions - currentQuestion else 0)

        return currentQuestion
    }

    fun nextQuestion(numberOfQuestions: Int): Int {
        currentQuestion = (currentQuestion + 1) % numberOfQuestions

        return currentQuestion
    }

    fun addScore(difficulty: String) {
        when (difficulty) {
            "Fácil" -> currentScore += 2
            "Medio" -> currentScore += 3
            "Difícil" -> currentScore += 4
        }
    }

    fun reduceScore() {
        if (currentScore - 2 < 0) {
            currentScore = 0
        } else {
            currentScore -= 2
        }
    }

    fun getTotalScore(difficulty: String): Int {
        return when (difficulty) {
            "Fácil" -> numberOfCorrectAnswers * 2
            "Medio" -> numberOfCorrectAnswers * 3
            "Difícil" -> numberOfCorrectAnswers * 4
            else ->  numberOfCorrectAnswers * 3
        }
    }

    fun getHintsPenalization(): Int {
        return numberOfHintsUsed * 2
    }

    private fun getGameTotalScore(difficulty: String, numberOfQuestions: Int): Int {
        return when (difficulty) {
            "Fácil" -> numberOfQuestions * 2
            "Medio" -> numberOfQuestions * 3
            "Difícil" -> numberOfQuestions * 4
            else ->  numberOfQuestions * 3
        }
    }

    fun getGameAverage(difficulty: String, numberOfQuestions: Int): String {
        return when ((getTotalScore(difficulty).toDouble() / getGameTotalScore(difficulty, numberOfQuestions)) * 100) {
            in 0.0..49.0 -> "bad_score"
            in 50.0..90.0 -> "medium_score"
            100.0 -> "perfect_score"
            else -> "bad_score"
        }
    }

    override fun toString(): String {
        return """
            {
                id: $id
                settings_id: $settingsId,
                score: $currentScore,
                question: $currentQuestion
                number_of_hints_available: $numberOfHintsAvailable
            }
        """.trimIndent()
    }
}
