package com.example.quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ScoreActivity : AppCompatActivity() {
    private lateinit var backButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        backButton = findViewById(R.id.score_back_home_button)

        backButton.setOnClickListener {
            finish()
        }
    }
}