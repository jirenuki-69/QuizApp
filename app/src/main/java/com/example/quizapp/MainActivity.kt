package com.example.quizapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.example.quizapp.db.AppDatabase
import com.example.quizapp.db.Daos.GameDao
import com.example.quizapp.db.Daos.GameQuestionDao
import com.example.quizapp.db.Daos.QuestionDao
import com.example.quizapp.db.Entities.Game
import com.example.quizapp.db.Entities.GameChoices
import com.example.quizapp.db.Entities.GameQuestion

class MainActivity : AppCompatActivity() {
    private lateinit var playButton: Button
    private lateinit var optionsButton: Button
    private lateinit var scoreButton: Button
    private lateinit var db: AppDatabase
    private lateinit var gameDao: GameDao
    private lateinit var questionsDao: QuestionDao
    private lateinit var gameQuestionsDao: GameQuestionDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        playButton = findViewById(R.id.button_play)
        optionsButton = findViewById(R.id.button_options)
        scoreButton = findViewById(R.id.score_button)

        db = AppDatabase.getInstance(this as Context)
        gameDao = db.GameDao()
        questionsDao = db.QuestionDao()
        gameQuestionsDao = db.GameQuestionDao()

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
            val currentGame = gameDao.getGameBySettingsId(1)

            if (currentGame == null) {
                constructGame()
                startActivity(intent)
            } else {
                val builder = AlertDialog.Builder(this as Context)
                builder.setTitle("Tienes un juego en curso")
                builder.setMessage("Quieres seguir o jugar otra partida?")
                builder.setPositiveButton("Continuar") { _, _ ->
                    startActivity(intent)
                }
                builder.setNegativeButton("Jugar otra") { _, _ ->
                    gameDao.delete(currentGame)
                    constructGame()
                    startActivity(intent)
                }

                builder.show()
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

    private fun constructGame() {
        val settings = db.SettingsDao().getFirstSettings()
        val newGame = Game(
            settingsId = settings.id,
            currentScore = 0,
            currentQuestion = 0
        )
        val selectedCategories = db.SettingsCategoriesDao().getCategories(settings.id)
        val categoryIds = selectedCategories.map { it.id }
        val questions = questionsDao.getRandomQuestions(categoryIds, settings.numberOfQuestions)

        newGame.setHints(settings.difficulty)
        val gameId = gameDao.insert(newGame)

        val gameQuestions = questions.mapIndexed { index, question ->
            GameQuestion(
                gameId = gameId.toInt(),
                questionId = question.id,
                questionNumber = index,
                isAnswered = false,
                isAnsweredByHint = false,
                hintsUsed = false,
                isCorrect = false
            )
        }

        val gameQuestionsIds = gameQuestionsDao.insertAll(gameQuestions)

        gameQuestionsIds.forEachIndexed { index, id ->
            val questionId = questions[index].id
            val choiceDao = db.ChoiceDao()
            val questionChoices = choiceDao.getChoices(questionId)

            when (settings.difficulty) {
                "Fácil" -> {
                    val correctChoice = questionChoices.find { it.isCorrect }
                    val incorrectChoice = questionChoices.shuffled().find { !it.isCorrect }

                    var gameChoices = listOf(
                        GameChoices(
                            gameQuestionId = id.toInt(),
                            choiceId = correctChoice!!.id,
                            isAnswered = false,
                            0
                        ),
                        GameChoices(
                            gameQuestionId = id.toInt(),
                            choiceId = incorrectChoice!!.id,
                            isAnswered = false,
                            1
                        )
                    )

                    gameChoices = gameChoices.shuffled()

                    val gameChoicesCopy = gameChoices.mapIndexed { index, gameChoice ->
                        gameChoice.positionNumber = index

                        return@mapIndexed gameChoice
                    }

                    db.GameChoicesDao().insertAll(gameChoicesCopy)
                }
                "Medio" -> {
                    val correctChoice = questionChoices.find { it.isCorrect }
                    val firstIncorrectChoice = questionChoices.find { !it.isCorrect }
                    questionChoices.remove(firstIncorrectChoice)

                    val secondIncorrectChoice = questionChoices.find { !it.isCorrect }

                    var gameChoices = listOf(
                        GameChoices(
                            gameQuestionId = id.toInt(),
                            choiceId = correctChoice!!.id,
                            isAnswered = false,
                            0
                        ),
                        GameChoices(
                            gameQuestionId = id.toInt(),
                            choiceId = firstIncorrectChoice!!.id,
                            isAnswered = false,
                            1
                        ),
                        GameChoices(
                            gameQuestionId = id.toInt(),
                            choiceId = secondIncorrectChoice!!.id,
                            isAnswered = false,
                            2
                        )
                    )

                    gameChoices = gameChoices.shuffled()

                    val gameChoicesCopy = gameChoices.mapIndexed { index, gameChoice ->
                        gameChoice.positionNumber = index

                        return@mapIndexed gameChoice
                    }

                    db.GameChoicesDao().insertAll(gameChoicesCopy)
                }
                "Difícil" -> {
                    val correctChoice = questionChoices.find { it.isCorrect }
                    val firstIncorrectChoice = questionChoices.find { !it.isCorrect }
                    questionChoices.remove(firstIncorrectChoice)

                    val secondIncorrectChoice = questionChoices.find { !it.isCorrect }
                    questionChoices.remove(secondIncorrectChoice)

                    val thirdIncorrectChoice = questionChoices.find { !it.isCorrect }

                    var gameChoices = listOf(
                        GameChoices(
                            gameQuestionId = id.toInt(),
                            choiceId = correctChoice!!.id,
                            isAnswered = false,
                            0
                        ),
                        GameChoices(
                            gameQuestionId = id.toInt(),
                            choiceId = firstIncorrectChoice!!.id,
                            isAnswered = false,
                            1
                        ),
                        GameChoices(
                            gameQuestionId = id.toInt(),
                            choiceId = secondIncorrectChoice!!.id,
                            isAnswered = false,
                            2
                        ),
                        GameChoices(
                            gameQuestionId = id.toInt(),
                            choiceId = thirdIncorrectChoice!!.id,
                            isAnswered = false,
                            3
                        )
                    )

                    gameChoices = gameChoices.shuffled()

                    val gameChoicesCopy = gameChoices.mapIndexed { index, gameChoice ->
                        gameChoice.positionNumber = index

                        return@mapIndexed gameChoice
                    }

                    db.GameChoicesDao().insertAll(gameChoicesCopy)
                }
            }
        }
    }
}