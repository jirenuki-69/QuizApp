package com.example.quizapp.Clases

data class GlobalScore(
    val profileId: Int,
    val profileName: String,
    val score: Int,
    val gamesPlayed: Int,
    val gamesUsingHint: Int
)