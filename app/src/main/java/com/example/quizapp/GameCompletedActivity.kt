package com.example.quizapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.quizapp.Clases.GameModel

class GameCompletedActivity : AppCompatActivity() {
    /**
     * * Views declaration
     */

    private lateinit var gameModel: GameModel
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

        /**
         * * Recibo los objetos que me envíe el game activity.
         */

        val bundle = intent!!.getBundleExtra("BUNDLE")
        gameModel = bundle!!.getParcelable<GameModel>("GAME_MODEL") as GameModel

        /**
         * * Views init
         */
        homeButton = findViewById(R.id.home_button)
        scoreImage = findViewById(R.id.score_image)
        scoreText = findViewById(R.id.score)
        firstText = findViewById(R.id.correct_questions_score)
        secondText = findViewById(R.id.hints_penalization)
        finalScoreText = findViewById(R.id.final_score)

        /**
         * * Texts
         */

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

        /**
         * * Image
         */

        // Image
        val imageSource = "gato_${gameModel.getGameAverage()}"
        val id = resources.getIdentifier(
            imageSource,
            "drawable",
            packageName
        )

        scoreImage.setImageDrawable(resources.getDrawable(id))

        /**
         * ? Si la puntuación del jugador no es perfecta, se le asigna un espaciado arriba a las otras imagenes
         * ? Esto debido al tamaño de las otras imagenes, simplementa para que se vea bien
         */

        if (gameModel.getGameAverage() != "perfect_score") {
            val params = scoreImage.layoutParams as ViewGroup.MarginLayoutParams
            params.setMargins(0, 90, 0, 0)
            scoreImage.layoutParams = params
        }

        /**
         * * Listeners
         */

        homeButton.setOnClickListener {
            finish()
        }
    }
}