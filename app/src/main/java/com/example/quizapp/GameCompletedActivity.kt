package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.quizapp.Clases.GameModel

class GameCompletedActivity : AppCompatActivity() {
    private lateinit var gameModel: GameModel
    private lateinit var gameButton: Button
    private lateinit var homeButton: Button
    private lateinit var scoreText: TextView
    private lateinit var firstText: TextView
    private lateinit var secondText: TextView
    private lateinit var finalScoreText: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_completed)

        val bundle = intent!!.getBundleExtra("BUNDLE")
        gameModel = bundle!!.getParcelable<GameModel>("GAME_MODEL") as GameModel

        // Views
        gameButton = findViewById(R.id.game_button)
        homeButton = findViewById(R.id.home_button)
        scoreText = findViewById(R.id.score)
        firstText = findViewById(R.id.correct_questions_score)
        secondText = findViewById(R.id.hints_penalization)
        finalScoreText = findViewById(R.id.final_score)

        scoreText.text = "${resources.getString(R.string.score_text)}"
        firstText.text = "${resources.getString(R.string.correct_questions_score)}: ${gameModel.getUserTotalScore()}"
        secondText.text = "${resources.getString(R.string.hints_penalization_text)}: ${gameModel.getHintsScorePenalization()}"
        finalScoreText.text = "${resources.getString(R.string.final_score_text)} ${gameModel.getUserFinalScore()}/${gameModel.getGameTotalScore()}"

        // Listeners
        gameButton.setOnClickListener {
            finish()
        }

        homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}