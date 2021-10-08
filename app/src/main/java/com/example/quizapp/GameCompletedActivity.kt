package com.example.quizapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.marginBottom
import androidx.core.view.marginTop
import com.example.quizapp.Clases.GameModel

class GameCompletedActivity : AppCompatActivity() {
    private lateinit var gameModel: GameModel
    private lateinit var gameButton: Button
    private lateinit var homeButton: Button
    private lateinit var scoreImage: ImageView
    private lateinit var scoreText: TextView
    private lateinit var firstText: TextView
    private lateinit var secondText: TextView
    private lateinit var finalScoreText: TextView

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_completed)

        val bundle = intent!!.getBundleExtra("BUNDLE")
        gameModel = bundle!!.getParcelable<GameModel>("GAME_MODEL") as GameModel

        // Views
        gameButton = findViewById(R.id.game_button)
        homeButton = findViewById(R.id.home_button)
        scoreImage = findViewById(R.id.score_image)
        scoreText = findViewById(R.id.score)
        firstText = findViewById(R.id.correct_questions_score)
        secondText = findViewById(R.id.hints_penalization)
        finalScoreText = findViewById(R.id.final_score)

        // Text
        scoreText.text = resources.getString(R.string.score_text)
        "${resources.getString(R.string.correct_questions_score)}: ${gameModel.getUserTotalScore()}".also {
            firstText.text = it
        }
        "${resources.getString(R.string.hints_penalization_text)}: ${gameModel.getHintsScorePenalization()}".also {
            secondText.text = it
        }
        "${resources.getString(R.string.final_score_text)} ${gameModel.getUserFinalScore()}/${gameModel.getGameTotalScore()}".also {
            finalScoreText.text = it
        }

        // Image
        val imageSource = "gato_${gameModel.getGameAverage()}"
        val id = resources.getIdentifier(
            imageSource,
            "drawable",
            packageName
        )

        scoreImage.setImageDrawable(resources.getDrawable(id))

        if (gameModel.getGameAverage() != "perfect_score") {
            val params = scoreImage.layoutParams as ViewGroup.MarginLayoutParams
            params.setMargins(0, 90, 0, 0)
            scoreImage.layoutParams = params
        }

        // Listeners
        gameButton.setOnClickListener {
            finish()
        }

        homeButton.setOnClickListener {
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.putExtra("OPTIONS_MODEL", gameModel.options)
            startActivity(intent)
        }
    }
}