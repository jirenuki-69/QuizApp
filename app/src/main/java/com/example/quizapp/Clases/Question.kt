package com.example.quizapp

import java.util.*
import kotlin.collections.ArrayList

data class Question(val text: String, val options: ArrayList<Pair<String, Boolean>>, var answered: Boolean = false, var isCorrect: Boolean = false)