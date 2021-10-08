package com.example.quizapp.Clases

import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.quizapp.Options
import com.example.quizapp.Question
import kotlinx.parcelize.Parcelize

@Parcelize
class GameModel(
    val options: Options?,
    private var currentQuestionIndex: Int = 0,
    var numberOfHintsAvailable: Int = when (options!!.difficulty) {
        "Fácil" -> 5
        "Medio" -> 3
        "Difícil" -> 1
        else -> 0
    },
    private var gameModelQuestions: Array<Question?> = arrayOfNulls<Question>(options!!.numberOfQuestions),
    var hintsUsed: Int = 0,
    var questionsAnswered: Int = 0,
    var correctAnswers: Int = 0,
    var correctAnswersWithoutHint: Int = 0,
) : ViewModel(), Parcelable {

    init {
        this.gameModelQuestions = getGameQuestions()
    }

    private val pointsCriteria = when (options!!.difficulty) {
        "Fácil" -> 1
        "Medio" -> 2
        "Difícil" -> 4
        else -> 0
    }

    private fun getGameQuestions(): Array<Question?> {
        val allGameQuestions = arrayOfNulls<Question>(options!!.numberOfQuestions)
        var arrayTemp = arrayListOf<Question>()

        options.categories.forEach { category ->
            category.questions.forEach { question -> arrayTemp.add(question) }
        }

        arrayTemp.shuffled().forEachIndexed { index, question ->
            if (index < options!!.numberOfQuestions) {
                val alteredOptions = shuffleQuestionOptionsByDifficulty(question.options)
                allGameQuestions[index] = Question(
                    question.text,
                    alteredOptions
                )
                allGameQuestions[index]!!.optionsAnswered = alteredOptions.map { false }
                    .toCollection(ArrayList())
            } else {
                return@forEachIndexed
            }
        }

        return allGameQuestions
    }

    private fun shuffleQuestionOptionsByDifficulty(questionOptions: ArrayList<Pareja>): ArrayList<Pareja> {
        when (options!!.difficulty) {
            "Fácil" -> {
                val correctAnswer = questionOptions.removeAt(0)
                val incorrectAnswer = questionOptions.random()

                return arrayListOf(correctAnswer, incorrectAnswer).shuffled()
                    .toCollection(ArrayList())
            }
            "Medio" -> {
                val correctAnswer = questionOptions.removeAt(0)
                val incorrectAnswer = questionOptions.random()

                questionOptions.remove(incorrectAnswer)

                val incorrectAnswer2 = questionOptions.random()

                return arrayListOf(correctAnswer, incorrectAnswer, incorrectAnswer2).shuffled()
                    .toCollection(ArrayList())

            }
            "Difícil" -> return questionOptions.shuffled().toCollection(ArrayList())
        }

        return arrayListOf<Pareja>()
    }

    fun getGameAverage(): String {
        return when ((getUserFinalScore().toDouble() / getGameTotalScore()) * 100) {
            in 0.0..49.0 -> "bad_score"
            in 50.0..90.0 -> "medium_score"
            100.0 -> "perfect_score"
            else -> "bad_score"
        }
    }

    fun getGameTotalScore(): Int {
        return options!!.numberOfQuestions * pointsCriteria
    }

    fun getUserTotalScore(): Int {
        return correctAnswers * pointsCriteria
    }

    fun getHintsScorePenalization(): Int {
        return hintsUsed * 2
    }

    fun getUserFinalScore(): Int {
        val finalScore = getUserTotalScore() - getHintsScorePenalization()

        if (finalScore < 0) {
            return 0
        }

        return finalScore
    }

    fun answerQuestionByHint(): Boolean {
        var count = 0

        getCurrentQuestion().optionsAnswered.forEach {
            if (!it) {
                count++
            }
        }

        return count == 2
    }

    fun getCurrentQuestionNumber(): Int {
        return currentQuestionIndex + 1
    }

    fun getCurrentQuestion(): Question {
        return gameModelQuestions[currentQuestionIndex]!!
    }

    fun previousQuestion(): Question {
        val size = gameModelQuestions.size - 1;
        currentQuestionIndex =
            size - (if (currentQuestionIndex > 0) gameModelQuestions.size - currentQuestionIndex else 0)

        return gameModelQuestions[currentQuestionIndex]!!
    }

    fun nextQuestion(): Question {
        currentQuestionIndex = (currentQuestionIndex + 1) % gameModelQuestions.size

        return gameModelQuestions[currentQuestionIndex]!!
    }
}