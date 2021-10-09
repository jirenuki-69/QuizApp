package com.example.quizapp.Clases

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import kotlinx.parcelize.Parcelize

/**
 * Clase que funciona para guardar los ajustes de la aplicación
 * @property allQuestions Todas las preguntas de la aplicación
 * @property difficulty La dificultad del juego. Default: Fácil
 * @property hintsAvailable Si las pistas están disponibles dentro del juego. Default: false
 * @property numberOfQuestions Int El número de preguntas que habrá en el juego. Default: 5
 * @property categories Las categorías seleccionadas para el juego
 */

@Parcelize
class Options(
    private var allQuestions: Array<ArrayList<Question>?>,
    var difficulty: String = "Fácil",
    var hintsAvailable: Boolean = false,
    var numberOfQuestions: Int = 5,
    val categories: ArrayList<Category> = arrayListOf()
) : ViewModel(), Parcelable {

    /**
     * Agregar/seleccionar una categoría a las opciones
     * @param categoryName El nombre de la categoría a agregar: [String]
     */

    fun putCategory(categoryName: String) {
        val category = Category(allQuestions)
        category.setCategoryName(categoryName)

        categories.add(category)
    }

    /**
     * Remover/deseleccionar una categoría de las opciones
     * @param categoryName El nombre de la categoría a remover: [String]
     */

    fun removeCategory(categoryName: String) {
        val index = categories.indexOfFirst { it.name == categoryName }
        categories.removeAt(index)
    }

    /**
     * Cambia la dificultad de acuerdo al número pasado
     * @param diff la dificultad a cambiar, 1 para fácil, 2 para medio y 3 para difícil
     */

    fun changeDifficulty(diff: Float) {
        when (diff) {
            1.0f -> difficulty = "Fácil"
            2.0f -> difficulty = "Medio"
            3.0f -> difficulty = "Difícil"
        }
    }
}