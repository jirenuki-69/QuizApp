package com.example.quizapp

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
class Category(
    val allQuestions: Array<ArrayList<Question>?>,
    var questions: ArrayList<Question> = arrayListOf<Question>(),
    var name: String = ""
) : Parcelable {
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