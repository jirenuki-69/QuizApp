package com.example.quizapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlin.collections.ArrayList

@Parcelize
data class Question(
    val text: String,
    val options: ArrayList<Pair<String, Boolean>>,
    var answered: Boolean = false,
    var isCorrect: Boolean = false
) : Parcelable