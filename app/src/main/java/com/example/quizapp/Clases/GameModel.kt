package com.example.quizapp.Clases

import androidx.lifecycle.ViewModel
import com.example.quizapp.Options
import com.example.quizapp.Question

class GameModel(val options: Options?) : ViewModel() {
    private var currentQuestionIndex: Int = 0
    var numberOfHintsAvaliable: Int = when (options!!.difficulty) {
        "Fácil" -> 5
        "Medio" -> 3
        "Difícil" -> 1
        else -> 0
    }
    var gameModelQuestions = getGameQuestions()
    var hintsUsed: Int = 0
    var correctAnswers: Int = 0
    var correctAnswersWithoutHint: Int = 0

    private fun getGameQuestions(): Array<Question?> {
        val allGameQuestions = arrayOfNulls<Question>(options!!.numberOfQuestions)
        var arrayTemp = arrayListOf<Question>()

        options.categories.forEach { category ->
            category.questions.forEach { question -> arrayTemp.add(question) }
        }

        arrayTemp.shuffled().forEachIndexed { index, question ->
            if (index < options!!.numberOfQuestions) {
                allGameQuestions[index] = Question(
                    question.text,
                    question.options.shuffled().toCollection(ArrayList())
                )
            }
        }

        return allGameQuestions
    }

    fun getNumberOfOptionsAnswered(): Int {
        var count = 0

        getCurrentQuestion().optionsAnswered.forEach {
            if (it) {
                count++
            }
        }

        return count
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