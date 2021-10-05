package com.example.quizapp.Clases

import com.example.quizapp.Options
import com.example.quizapp.Question

class GameModel(val options: Options) {
    var currentQuestionIndex: Int = 0
    var numberOfHintsAvaliable: Int = 0

    var gameModelQuestions = getGameQuestions()

    private fun getGameQuestions(): Array<Question?> {
        val allGameQuestions = arrayOfNulls<Question>(options.numberOfQuestions)
        var arrayTemp =  arrayOf<Question>()

        options.getCategories().forEach { category ->
            arrayTemp = category.getQuestions().map { question -> question }.toTypedArray()
        }

        arrayTemp.forEachIndexed { index, question ->
            if (index + 1 != options.numberOfQuestions) {
                allGameQuestions[index] = question
            }
        }

        return allGameQuestions
    }

    fun getCurrentQuestion() {}

    fun previousQuestion() {}

    fun nextQuestion() {}
}