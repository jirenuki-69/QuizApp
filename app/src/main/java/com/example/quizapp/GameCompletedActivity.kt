package com.example.quizapp

import android.annotation.SuppressLint
import android.content.Context
import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.Clases.ScoreAdapter
import com.example.quizapp.db.AppDatabase
import com.example.quizapp.db.Entities.Profile
import com.example.quizapp.db.Entities.Score
import com.example.quizapp.db.Entities.Settings
import java.util.*

class GameCompletedActivity : AppCompatActivity() {
    private lateinit var homeButton: Button
    private lateinit var scoreImage: ImageView
    private lateinit var scoreText: TextView
    private lateinit var rv: RecyclerView
    private lateinit var db: AppDatabase
    private lateinit var profile: Profile
    private lateinit var settings: Settings

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_completed)

        db = AppDatabase.getInstance(this as Context)
        profile = db.ProfileDao().getActiveProfile()!!
        settings = db.SettingsDao().getProfileSettings(profile.id)
        val completedGame = db.GameDao().getProfileLastCompletedGame(profile.id)

        val scores = db.ScoreDao().getBestFive()

        homeButton = findViewById(R.id.home_button)
        scoreImage = findViewById(R.id.score_image)
        scoreText = findViewById(R.id.score)
        rv = findViewById(R.id.scores_rv)

        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = ScoreAdapter(scores)

        "${resources.getString(R.string.score_text)}: ${completedGame?.currentScore}".also {
            scoreText.text = it
        }

        val imageSource = "gato_${completedGame?.getGameAverage(settings.difficulty, settings.numberOfQuestions)}"
        val id = resources.getIdentifier(
            imageSource,
            "drawable",
            packageName
        )

        scoreImage.setImageDrawable(resources.getDrawable(id))

        homeButton.setOnClickListener {
            createScoreData()
            setResult(RESULT_CANCELED)
            finish()
        }
    }

    private fun createScoreData() {
        val completedGame = db.GameDao().getGameBySettingsId(settings.id)
        val currentDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        val score = Score(
            profileId = profile.id,
            date = currentDate,
            difficulty = settings.difficulty,
            numberOfQuestions = settings.numberOfQuestions,
            hintsEnabled = settings.hintsEnabled,
            totalScore = completedGame!!.currentScore
        )
        db.ScoreDao().insert(score)
    }
}