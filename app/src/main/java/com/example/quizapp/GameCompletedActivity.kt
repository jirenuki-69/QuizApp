package com.example.quizapp

import android.annotation.SuppressLint
import android.content.Context
import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.quizapp.db.AppDatabase
import com.example.quizapp.db.Entities.Score
import com.example.quizapp.db.Entities.Settings
import java.util.*

class GameCompletedActivity : AppCompatActivity() {
    private lateinit var homeButton: Button
    private lateinit var scoreImage: ImageView
    private lateinit var scoreText: TextView
    private lateinit var firstText: TextView
    private lateinit var secondText: TextView
    private lateinit var finalScoreText: TextView
    private lateinit var db: AppDatabase
    private lateinit var settings: Settings

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_completed)

        db = AppDatabase.getInstance(this as Context)
        settings = db.SettingsDao().getFirstSettings()
        val completedGame = db.GameDao().getGameBySettingsId(settings.id)

        homeButton = findViewById(R.id.home_button)
        scoreImage = findViewById(R.id.score_image)
        scoreText = findViewById(R.id.score)
        firstText = findViewById(R.id.correct_questions_score)
        secondText = findViewById(R.id.hints_penalization)
        finalScoreText = findViewById(R.id.final_score)

        scoreText.text = resources.getString(R.string.score_text)
        "${resources.getString(R.string.correct_questions_score)}: ${completedGame?.getTotalScore(settings.difficulty)}".also {
            firstText.text = it
        }
        "${resources.getString(R.string.hints_penalization_text)}: ${completedGame?.getHintsPenalization()}".also {
            secondText.text = it
        }
        "${resources.getString(R.string.final_score_text)}: ${completedGame?.currentScore}".also {
            finalScoreText.text = it
        }

        val imageSource = "gato_${completedGame?.getGameAverage(settings.difficulty, settings.numberOfQuestions)}"
        val id = resources.getIdentifier(
            imageSource,
            "drawable",
            packageName
        )

        scoreImage.setImageDrawable(resources.getDrawable(id))

        if (completedGame?.getGameAverage(settings.difficulty, settings.numberOfQuestions) != "perfect_score") {
            val params = scoreImage.layoutParams as ViewGroup.MarginLayoutParams
            params.setMargins(0, 90, 0, 0)
            scoreImage.layoutParams = params
        }

        homeButton.setOnClickListener {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        createScoreData()
        db.GameDao().deleteBySettingsId(settings.id)
    }

    private fun createScoreData() {
        val completedGame = db.GameDao().getGameBySettingsId(settings.id)
        val currentDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        val score = Score(
            date = currentDate,
            difficulty = settings.difficulty,
            numberOfQuestions = settings.numberOfQuestions,
            hintsEnabled = settings.hintsEnabled,
            totalScore = completedGame!!.currentScore
        )
        db.ScoreDao().insert(score)
    }
}