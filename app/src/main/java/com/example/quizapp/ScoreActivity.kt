package com.example.quizapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.Clases.GlobalScoreAdapter
import com.example.quizapp.db.AppDatabase

class ScoreActivity : AppCompatActivity() {
    private lateinit var backButton: Button
    private lateinit var autoComplete: AutoCompleteTextView
    private lateinit var adapter: GlobalScoreAdapter
    private lateinit var orderAdapter: ArrayAdapter<String>
    private lateinit var scoresRv: RecyclerView
    private lateinit var db: AppDatabase
    private lateinit var orderCriteria: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        supportActionBar?.hide()

        db = AppDatabase.getInstance(this as Context)
        val scores = db.ScoreDao().getGlobalScores()

        backButton = findViewById(R.id.score_back_home_button)
        autoComplete = findViewById(R.id.autoCompleteTextView)
        scoresRv = findViewById(R.id.score_scores_rv)
        scoresRv.layoutManager = LinearLayoutManager(this)

        adapter = GlobalScoreAdapter(scores) { onSelectedProfile(it) }
        scoresRv.adapter = adapter

        backButton.setOnClickListener {
            finish()
        }

        autoComplete.setOnItemClickListener { parent, view, position, id ->
            if (position == 0) {
                adapter.clear()
                val newScores = db.ScoreDao().getGlobalScores()
                adapter.addAll(newScores)
            } else {
                adapter.clear()
                val newScores = db.ScoreDao().getGlobalScoresOrderedByGamesPlayed()
                adapter.addAll(newScores)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        orderCriteria = resources.getStringArray(R.array.globalOrderBy)
        orderAdapter = ArrayAdapter(this as Context, R.layout.dropdown_item, orderCriteria)
        autoComplete.setAdapter(orderAdapter)
    }

    private fun onSelectedProfile(position: Int) {
        ProfileScoresActivity.startActivity(this, adapter.getItem(position).profileId)
    }
}