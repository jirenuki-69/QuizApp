package com.example.quizapp.Clases

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import kotlinx.parcelize.Parcelize

@Parcelize
class Options(
    private var allQuestions: Array<ArrayList<Question>?>,
    var difficulty: String = "Fácil",
    var hintsAvailable: Boolean = false,
    var numberOfQuestions: Int = 5,
    val categories: ArrayList<Category> = arrayListOf()
) : ViewModel(), Parcelable {

    fun putCategory(categoryName: String) {
        val category = Category(allQuestions)
        category.setCategoryName(categoryName)

        categories.add(category)
    }

    fun removeCategory(categoryName: String) {
        val index = categories.indexOfFirst { it.name == categoryName }
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