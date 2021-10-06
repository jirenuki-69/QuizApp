package com.example.quizapp.Clases

import android.util.Log
import com.example.quizapp.Options
import com.example.quizapp.Question

class GameModel(val options: Options) {
    private var currentQuestionIndex: Int = 0
    var numberOfHintsAvaliable: Int = 0

    var gameModelQuestions = getGameQuestions()

    private fun getGameQuestions(): Array<Question?> {
        val allGameQuestions = arrayOfNulls<Question>(options.numberOfQuestions)
        var arrayTemp = arrayListOf<Question>()

        options.getCategories().forEach { category ->
            category.getQuestions().forEach { question -> arrayTemp.add(question) }
        }


        arrayTemp.shuffled().forEachIndexed { index, question ->
            if (index < options.numberOfQuestions) {
                allGameQuestions[index] = Question(
                    question.text,
                    question.options.shuffled().toCollection(ArrayList())
                )
            }
        }

        return allGameQuestions
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