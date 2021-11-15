package com.example.quizapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.Clases.ScoreAdapter
import com.example.quizapp.db.AppDatabase

class ScoreActivity : AppCompatActivity() {
    private lateinit var backButton: Button
    private lateinit var scoresRv: RecyclerView
    private lateinit var db: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        supportActionBar?.hide()

        db = AppDatabase.getInstance(this as Context)
        val scores = db.ScoreDao().getAll()

        backButton = findViewById(R.id.score_back_home_button)
        scoresRv = findViewById(R.id.score_scores_rv)
        scoresRv.layoutManager = LinearLayoutManager(this)

        scoresRv.adapter = ScoreAdapter(scores)

        backButton.setOnClickListener {
            finish()
        }
    }
}