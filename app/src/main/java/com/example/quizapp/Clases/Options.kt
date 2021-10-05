package com.example.quizapp

import android.content.Context
import java.io.Serializable

class Options(private val context: Context) : Serializable {
    private val categories = arrayListOf<Category>()
    private var difficulty = "Fácil"
    var hintsAvailable = false
    var numberOfQuestions = 5

    fun getCategories(): ArrayList<Category> {
        return categories
    }

    fun putCategory(categoryName: String) {
        val category = Category(context)
        category.setName(categoryName)

        categories.add(category)
    }

    fun removeCategory(categoryName: String) {
        val index = categories.indexOfFirst { it.getName() == categoryName }
        categories.removeAt(index)
    }

    fun changeDifficulty(diff: Float) {
        when (diff) {
            1.0f -> difficulty = "Fácil"
            2.0f -> difficulty = "Medio"
            3.0f -> difficulty = "Difícil"
        }
    }

    fun setAllOptions(categoriesTmp: MutableList<Category>, diff: String, hints: Boolean, questions: Int) {
        categories.clear()
        categoriesTmp.map { categories.add(it) }
        difficulty = diff
        hintsAvailable = hints
        numberOfQuestions = questions
    }
}