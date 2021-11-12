package com.example.quizapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.example.quizapp.Clases.Options
import com.example.quizapp.Clases.Pareja
import com.example.quizapp.Clases.Question
import com.example.quizapp.Clases.viewModelFactory
import com.example.quizapp.db.AppDatabase
import com.example.quizapp.db.Category.Category
import com.example.quizapp.db.Game.Game
import com.example.quizapp.db.Game.GameDao
import java.lang.Error

class MainActivity : AppCompatActivity() {
    private lateinit var playButton: Button
    private lateinit var optionsButton: Button
    private lateinit var scoreButton: Button
    private lateinit var db: AppDatabase
    private lateinit var gameDao: GameDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        playButton = findViewById(R.id.button_play)
        optionsButton = findViewById(R.id.button_options)
        scoreButton = findViewById(R.id.score_button)

        db = AppDatabase.getInstance(this as Context)
        gameDao = db.GameDao()

        val currentGame = gameDao.getGameBySettingsId(1)

        Log.d("QUIZ_APP_DEBUG", currentGame.toString())

        val resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) {
                    Toast.makeText(
                        this,
                        resources.getString(R.string.saving_options),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        playButton.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)

            if (currentGame == null) {
                startActivity(intent)
            }
        }

        optionsButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            resultLauncher.launch(intent)
        }

        scoreButton.setOnClickListener {
            val intent = Intent(this, ScoreActivity::class.java)

            startActivity(intent)
        }
    }
}