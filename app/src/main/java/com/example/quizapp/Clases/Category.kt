package com.example.quizapp.Clases

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Clase que para guardar una categoría y también las preguntas de la muisma
 * @property allQuestions Todas las preguntas de la aplicación
 * @property questions Es un arreglo de tipo Question que almacena todas las preguntas de esa categoría
 * @property name El nombre de la categoría
 */

@Parcelize
class Category(
    private val allQuestions: Array<ArrayList<Question>?>,
    var questions: ArrayList<Question> = arrayListOf(),
    var name: String = ""
) : Parcelable {

    /**
     * Asignar el nombre de la categoría y settear las preguntas de acuerdo al nombre proporcionado
     * @name El nombre a asignar a la categoría: [String]
     */

    fun setCategoryName(name: String) {
        this.name = name
        when (name) {
            "video_games" -> questions = allQuestions[0]!!
            "mario_bros" -> questions = allQuestions[1]!!
            "spider_man" -> questions = allQuestions[2]!!
            "cars" -> questions = allQuestions[3]!!
            "dragon_ball" -> questions = allQuestions[4]!!
            "terminal_montage" -> questions = allQuestions[5]!!
        }
    }
}