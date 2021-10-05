package com.example.quizapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Options(
    private var allQuestions: Array<ArrayList<Question>?>,
    private var difficulty: String = "Fácil",
    var hintsAvailable: Boolean = false,
    var numberOfQuestions: Int = 5,
    private val categories: ArrayList<Category> = arrayListOf<Category>()
) : Parcelable {

    fun getCategories(): ArrayList<Category> {
        return categories
    }

    fun getDifficulty(): String {
        return difficulty
    }

    fun putCategory(categoryName: String) {
        val category = Category(allQuestions)
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
}