package com.example.quizapp.Clases

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlin.collections.ArrayList

/**
 * Clase que sirve para guardar los datos para cada pregunta
 * @property text Es el texto de la pregunta
 * @property options Es un arreglo de tipo "Pareja" que sirve para almacenar las diferentes opciones de la pregunta
 * @property answered Sirve para conocer si la pregunta ya fue respondida
 * @property answeredByHint Sirve para conocer si para responder la pregunta, se utilizaron pistas
 * @property isCorrect Es para conocer si fue respondida correctamente
 * @property hintsUsed Es para conocer si se utilizó pistas, ya sea si respondío correcta o incorrectamente
 * @property optionsAnswered Es un arreglo de Booleanos que siempre tendrá el mismo tamaño que los "options". Sirve para conocer qué opciones han sido seleccionadas
 */

@Parcelize
data class Question(
    val text: String,
    val options: ArrayList<Pareja>,
    var answered: Boolean = false,
    var answeredByHint: Boolean = false,
    var isCorrect: Boolean = false,
    var hintsUsed: Boolean = false,
    var optionsAnswered: ArrayList<Boolean> = arrayListOf()
) : Parcelable