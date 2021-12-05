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
import com.example.quizapp.db.Daos.*
import com.example.quizapp.db.Entities.Game
import com.example.quizapp.db.Entities.GameChoices
import com.example.quizapp.db.Entities.GameQuestion
import com.example.quizapp.db.Entities.Profile

class MainActivity : AppCompatActivity() {
    private lateinit var playButton: Button
    private lateinit var optionsButton: Button
    private lateinit var scoreButton: Button
    private lateinit var profileButton: Button
    private lateinit var db: AppDatabase
    private lateinit var profileDao: ProfileDao
    private lateinit var settingsDao: SettingsDao
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
        profileButton = findViewById(R.id.profile_button)

        db = AppDatabase.getInstance(this as Context)
        gameDao = db.GameDao()
        questionsDao = db.QuestionDao()
        gameQuestionsDao = db.GameQuestionDao()
        profileDao = db.ProfileDao()
        settingsDao = db.SettingsDao()

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
            val currentProfile = profileDao.getActiveProfile()

            if (currentProfile != null) {
                val currentGame = gameDao.getProfileActiveGame(currentProfile.id)
                val intent = Intent(this, GameActivity::class.java)

                if (currentGame == null) {
                    constructGame(currentProfile)
                    startActivity(intent)
                } else {
                    val builder = AlertDialog.Builder(this as Context)
                    builder.setTitle(resources.getString(R.string.current_game_text))
                    builder.setMessage(resources.getString(R.string.continue_or_new_text))
                    builder.setPositiveButton(resources.getString(R.string.continue_text)) { _, _ ->
                        startActivity(intent)
                    }
                    builder.setNegativeButton(resources.getString(R.string.play_another_game_text)) { _, _ ->
                        gameDao.delete(currentGame)
                        constructGame(currentProfile)
                        startActivity(intent)
                    }

                    builder.show()
                }
            } else {
                Toast.makeText(
                    this as Context,
                    resources.getString(R.string.profile_needed_game),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        optionsButton.setOnClickListener {
            val currentProfile = profileDao.getActiveProfile()

            if (currentProfile != null) {
                val intent = Intent(this, SettingsActivity::class.java)
                resultLauncher.launch(intent)
            } else {
                Toast.makeText(
                    this as Context,
                    resources.getString(R.string.profile_needed),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        scoreButton.setOnClickListener {
            val intent = Intent(this, ScoreActivity::class.java)

            startActivity(intent)
        }

        profileButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)

            startActivity(intent)
        }
    }

    private fun constructGame(currentProfile: Profile) {
        val settings = settingsDao.getProfileSettings(currentProfile.id)
        val newGame = Game(
            settingsId = settings.id,
            currentScore = 0,
            currentQuestion = 0,
            finished = false,
            profileId = currentProfile.id
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