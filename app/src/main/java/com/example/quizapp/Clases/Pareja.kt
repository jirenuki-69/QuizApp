package com.example.quizapp.Clases

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pareja(val first: String, val second: Boolean) : Parcelable