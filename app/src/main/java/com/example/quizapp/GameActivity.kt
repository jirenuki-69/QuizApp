package com.example.quizapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.quizapp.Clases.*
import com.example.quizapp.db.AppDatabase
import com.example.quizapp.db.Daos.ChoiceDao
import com.example.quizapp.db.Daos.GameChoicesDao
import com.example.quizapp.db.Daos.GameDao
import com.example.quizapp.db.Daos.GameQuestionDao
import com.example.quizapp.db.Entities.*
import com.example.quizapp.db.Entities.Question
import com.google.android.material.snackbar.Snackbar

class GameActivity : AppCompatActivity() {
    private lateinit var listViewOptions: ListView
    private lateinit var questionText: TextView
    private lateinit var hintsButton: Button
    private lateinit var nextButton: Button
    private lateinit var previousButton: Button
    private lateinit var questionCounter: TextView
    private lateinit var adapter: OptionAdapter
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private lateinit var db: AppDatabase
    private lateinit var game: Game
    private lateinit var settings: Settings
    private lateinit var gameDao: GameDao
    private lateinit var gameQuestionsDao: GameQuestionDao
    private lateinit var gameChoicesDao: GameChoicesDao
    private lateinit var choiceDao: ChoiceDao
    private lateinit var currentGameQuestion: GameQuestion

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        supportActionBar?.hide()

        db = AppDatabase.getInstance(this as Context)

        questionText = findViewById(R.id.question_text)
        listViewOptions = findViewById(R.id.list_view)
        nextButton = findViewById(R.id.next_button)
        hintsButton = findViewById(R.id.hints_button)
        previousButton = findViewById(R.id.prev_button)
        questionCounter = findViewById(R.id.numero_de_preguntas)

        gameDao = db.GameDao()
        gameQuestionsDao = db.GameQuestionDao()
        gameChoicesDao = db.GameChoicesDao()
        choiceDao = db.ChoiceDao()

        game = gameDao.getGameBySettingsId(1)!!
        settings = gameDao.getSettings(game.id)

        updateNumberCounter(
            game.currentQuestion + 1,
            settings.numberOfQuestions
        )

        "${resources.getString(R.string.hints_text)}: ${game.numberOfHintsAvailable}".also {
            hintsButton.text = it
        }

        hintsButton.isVisible = settings.hintsEnabled

        currentGameQuestion = gameQuestionsDao.getGameQuestion(game.id, game.currentQuestion)

        questionText.text = gameQuestionsDao.getRealQuestion(currentGameQuestion.questionId).text

        val startOptions = gameChoicesDao.getGameChoices(currentGameQuestion.id)
            .toCollection(ArrayList())
        val optionsTexts = gameChoicesDao.getGameChoicesText(currentGameQuestion.id)
            .toCollection(ArrayList())

//        val startOptionsCopy = arrayListOf<String>()
//        startOptionsCopy.addAll(startOptions)

        adapter = OptionAdapter(this, optionsTexts)

        listViewOptions.adapter = adapter

        manageOptionsState(startOptions)

        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_CANCELED) {
                    finish()
                }
            }

        listViewOptions.setOnItemClickListener { _, view, position, _ ->
            if (currentGameQuestion.isAnswered) {
                questionAlreadyAnsweredSnack(view)

                return@setOnItemClickListener
            }

            val gameChoice = gameChoicesDao.getGameChoice(currentGameQuestion.id, position)

            currentGameQuestion.isAnswered = true
            gameChoice.isAnswered = true

            val isCorrect = choiceDao.getChoice(gameChoice.choiceId).isCorrect

            if (isCorrect) {
                game.numberOfCorrectAnswers++
                game.addScore(settings.difficulty)

                if (!currentGameQuestion.hintsUsed) {
                    game.correctAnswersWithoutHint++
                }

                if (game.correctAnswersWithoutHint % 2 == 0 && game.correctAnswersWithoutHint > 0) {
                    game.numberOfHintsAvailable++
                    "${resources.getString(R.string.hints_text)}: ${game.numberOfHintsAvailable}".also {
                        hintsButton.text = it
                    }
                }
            }

            view.setBackgroundColor(
                when (isCorrect) {
                    true -> {
                        when (currentGameQuestion.hintsUsed) {
                            true -> Color.parseColor(resources.getString(R.color.black))
                            false -> Color.parseColor(resources.getString(R.color.green))
                        }
                    }
                    false -> Color.parseColor(resources.getString(R.color.red))
                }
            )

            val snackText = when (isCorrect) {
                true -> resources.getString(R.string.correct_text)
                false -> resources.getString(R.string.incorrect_text)
            }
            val snack = Snackbar.make(this, view, snackText, Snackbar.LENGTH_SHORT)
            snack.setBackgroundTint(Color.parseColor(resources.getString(R.color.primary_blue)))
            snack.setTextColor(Color.parseColor(resources.getString(R.color.white)))
            snack.show()

            game.questionsAnswered++

            gameQuestionsDao.update(currentGameQuestion)
            gameChoicesDao.update(gameChoice)

            toGameResults()
        }

        hintsButton.setOnClickListener {
            if (currentGameQuestion.isAnswered) {
                questionAlreadyAnsweredSnack(it)

                return@setOnClickListener
            }

            game.numberOfHintsUsed++
            game.reduceScore()

            if (game.numberOfHintsAvailable == 0) {
                val snack = Snackbar.make(
                    this,
                    it,
                    resources.getString(R.string.no_hints),
                    Snackbar.LENGTH_SHORT
                )
                snack.setBackgroundTint(Color.parseColor(resources.getString(R.color.primary_blue)))
                snack.setTextColor(Color.parseColor(resources.getString(R.color.white)))
                snack.show()

                return@setOnClickListener
            }

            game.numberOfHintsAvailable--

            "${resources.getString(R.string.hints_text)}: ${game.numberOfHintsAvailable}".also { string ->
                hintsButton.text = string
            }

            updateQuestionsByHint()
        }

        previousButton.setOnClickListener { updateQuestionValues("PREVIOUS") }

        nextButton.setOnClickListener { updateQuestionValues("NEXT") }
    }

    override fun onDestroy() {
        super.onDestroy()
        gameDao.update(game)
    }

    private fun updateNumberCounter(currentQuestion: Int, totalOfQuestions: Int) {
        "$currentQuestion/$totalOfQuestions".also { questionCounter.text = it }
    }

    private fun updateQuestionValues(command: String) {
        when (command) {
            "PREVIOUS" -> {
                val previousQuestionIndex = game.previousQuestion(settings.numberOfQuestions)
                currentGameQuestion =
                    gameQuestionsDao.getGameQuestion(game.id, previousQuestionIndex)
                questionText.text =
                    gameQuestionsDao.getRealQuestion(currentGameQuestion.questionId).text
            }
            "NEXT" -> {
                val nextQuestionIndex = game.nextQuestion(settings.numberOfQuestions)
                currentGameQuestion = gameQuestionsDao.getGameQuestion(game.id, nextQuestionIndex)
                questionText.text =
                    gameQuestionsDao.getRealQuestion(currentGameQuestion.questionId).text
            }
        }

        val choices =
            gameChoicesDao.getGameChoices(currentGameQuestion.id).toCollection(ArrayList())

        manageOptionsState(choices)

        updateNumberCounter(
            currentGameQuestion.questionNumber + 1,
            settings.numberOfQuestions
        )
    }

    @SuppressLint("ResourceType")
    private fun questionAlreadyAnsweredSnack(view: View) {
        val snackText = resources.getString(R.string.question_already_answered)
        val snack = Snackbar.make(this, view, snackText, Snackbar.LENGTH_SHORT)
        snack.setBackgroundTint(Color.parseColor(resources.getString(R.color.primary_blue)))
        snack.setTextColor(Color.parseColor(resources.getString(R.color.white)))
        snack.show()
    }

    private fun toGameResults() {
        val questionsAnswered = gameQuestionsDao.getAllQuestionsAnswered(game.id)

        if (questionsAnswered.size == settings.numberOfQuestions) {
            gameDao.update(game)

            val intent = Intent(this, GameCompletedActivity::class.java)
            resultLauncher.launch(intent)
        }
    }

    @SuppressLint("ResourceType")
    private fun manageOptionsState(gameChoices: ArrayList<GameChoices>) {
        val choicesTexts = gameChoicesDao.getGameChoicesText(currentGameQuestion.id)
        adapter.clear()
        adapter.addAll(choicesTexts)
        adapter.notifyDataSetChanged()

        gameChoices.forEachIndexed() { index, gameChoice ->
            listViewOptions.getChildAt(index)?.setBackgroundColor(
                when (gameChoice.isAnswered) {
                    true -> {
                        val choice = choiceDao.getChoice(gameChoice.choiceId)
                        if (choice.isCorrect) {
                            Color.parseColor(resources.getString(if (currentGameQuestion.hintsUsed) R.color.black else R.color.green))
                        } else {
                            Color.parseColor(resources.getString(R.color.red))
                        }
                    }
                    false -> Color.parseColor(resources.getString(R.color.primary_blue))
                }
            )
        }
    }

    @SuppressLint("ResourceType")
    private fun updateQuestionsByHint() {
        val question = currentGameQuestion
        val answeredChoices = gameChoicesDao.getAnsweredChoices(question.id)
        question.hintsUsed = true

        if (answerQuestionByHint(answeredChoices)) {
            val correctChoice = gameChoicesDao.getCorrectChoice(gameQuestionsDao.getRealQuestion(currentGameQuestion.questionId).id)

            game.addScore(settings.difficulty)
            game.numberOfCorrectAnswers++
            correctChoice.isAnswered = true
            question.isAnswered = true
            question.isAnsweredByHint = true
            listViewOptions.getChildAt(correctChoice.positionNumber)
                .setBackgroundColor(Color.parseColor(resources.getString(R.color.black)))

            gameQuestionsDao.update(question)
            gameChoicesDao.update(correctChoice)
            toGameResults()

            return
        }

        val incorrectChoice = gameChoicesDao.getRandomIncorrectChoice(question.id)

        incorrectChoice.isAnswered = true
        listViewOptions.getChildAt(incorrectChoice.positionNumber)
            .setBackgroundColor(Color.parseColor(resources.getString(R.color.red)))

        gameChoicesDao.update(incorrectChoice)
    }

    private fun answerQuestionByHint(list: List<GameChoices>): Boolean {
        return (settings.getNumberOfChoices() - list.count()) == 2
    }
}