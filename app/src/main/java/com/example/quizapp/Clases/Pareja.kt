package com.example.quizapp.Clases

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Clase que sirve para almacenar las opciones de una pregunta
 * @property first El texto de la opción
 * @property second Booleano para conocer si esa opción es la correcta (true) o incorrecta (false)
 */

@Parcelize
data class Pareja(val first: String, val second: Boolean) : Parcelable