package com.example.quizapp.Clases

import android.util.Log
import com.example.quizapp.Options
import com.example.quizapp.Question

class GameModel(val options: Options) {
    var currentQuestionIndex: Int = 0
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
                allGameQuestions[index] = question
            }
        }

        return allGameQuestions
    }

    fun getCurrentQuestion() {}

    fun previousQuestion() {}

    fun nextQuestion() {}
}