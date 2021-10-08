package com.example.quizapp.Clases

import android.os.Parcelable
import com.example.quizapp.Clases.Pareja
import kotlinx.parcelize.Parcelize
import kotlin.collections.ArrayList

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