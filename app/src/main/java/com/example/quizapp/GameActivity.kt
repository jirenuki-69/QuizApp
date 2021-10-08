package com.example.quizapp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.quizapp.Clases.GameModel
import com.example.quizapp.Clases.OptionAdapter
import com.example.quizapp.Clases.Pareja
import com.example.quizapp.Clases.viewModelFactory
import com.google.android.material.snackbar.Snackbar

class GameActivity : AppCompatActivity() {
    private lateinit var listViewOptions: ListView
    private lateinit var questionText: TextView
    private lateinit var hintsButton: Button
    private lateinit var nextButton: Button
    private lateinit var previousButton: Button
    private lateinit var questionCounter: TextView
    private lateinit var adapter: OptionAdapter
    private lateinit var gameModel: GameModel

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val bundle = intent!!.getBundleExtra("BUNDLE")
        val optionsModel = bundle!!.getParcelable("OPTIONS_MODEL") as? Options

        questionText = findViewById(R.id.question_text)
        listViewOptions = findViewById(R.id.list_view)
        nextButton = findViewById(R.id.next_button)
        hintsButton = findViewById(R.id.hints_button)
        previousButton = findViewById(R.id.prev_button)
        questionCounter = findViewById(R.id.numero_de_preguntas)

        gameModel = ViewModelProvider(
            this,
            viewModelFactory { GameModel(optionsModel) }
        )[GameModel::class.java]

        updateNumberCounter(
            gameModel.getCurrentQuestionNumber(),
            gameModel.options!!.numberOfQuestions
        )

        hintsButton.text =
            "${resources.getString(R.string.hints_text)}: ${gameModel.numberOfHintsAvailable}"

        hintsButton.isVisible = gameModel.options!!.hintsAvailable

        questionText.text = gameModel.getCurrentQuestion().text

        val startOptions = gameModel.getCurrentQuestion().options!!.map { option -> option }
            .toCollection(ArrayList())

        val startOptionsCopy = arrayListOf<Pareja>()
        startOptionsCopy.addAll(startOptions)

        adapter = OptionAdapter(this, startOptions)

        listViewOptions.adapter = adapter

        manageOptionsState(startOptionsCopy)

        listViewOptions.setOnItemClickListener { _, view, position, _ ->
            val question = gameModel.getCurrentQuestion()

            if (question.answered) {
                questionAlreadyAnsweredSnack(view)

                return@setOnItemClickListener
            }

            question.answered = true
            question.optionsAnswered[position] = true

            val isCorrect = adapter.getItem(position)!!.second

            if (isCorrect) {
                gameModel.correctAnswers++

                if (!question.hintsUsed) {
                    gameModel.correctAnswersWithoutHint++
                }

                if (gameModel.correctAnswersWithoutHint % 2 == 0 && gameModel.correctAnswersWithoutHint > 0) {
                    gameModel.numberOfHintsAvailable++
                    hintsButton.text =
                        "${resources.getString(R.string.hints_text)}: ${gameModel.numberOfHintsAvailable}"
                }
            }

            view.setBackgroundColor(
                when (isCorrect) {
                    true -> {
                        when (question.hintsUsed) {
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

            gameModel.questionsAnswered++

            toGameResults()
        }

        hintsButton.setOnClickListener {
            val question = gameModel.getCurrentQuestion()

            if (question.answered) {
                questionAlreadyAnsweredSnack(it)

                return@setOnClickListener
            }

            if (gameModel.numberOfHintsAvailable == 0) {
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

            gameModel.numberOfHintsAvailable--

            "${resources.getString(R.string.hints_text)}: ${gameModel.numberOfHintsAvailable}".also { string ->
                hintsButton.text = string
            }

            gameModel.hintsUsed++
            updateQuestionsByHint()
        }

        previousButton.setOnClickListener { updateQuestionValues("PREVIOUS") }

        nextButton.setOnClickListener { updateQuestionValues("NEXT") }
    }

    private fun updateNumberCounter(currentQuestion: Int, totalOfQuestions: Int) {
        questionCounter.text = "$currentQuestion/$totalOfQuestions"
    }

    private fun updateQuestionValues(command: String) {
        when (command) {
            "PREVIOUS" -> questionText.text = gameModel.previousQuestion().text
            "NEXT" -> questionText.text = gameModel.nextQuestion().text
        }

        val options = gameModel.getCurrentQuestion().options.map { option -> option }
            .toCollection(ArrayList())

        manageOptionsState(options)

        updateNumberCounter(
            gameModel.getCurrentQuestionNumber(),
            gameModel.options!!.numberOfQuestions
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
        if (gameModel.questionsAnswered == gameModel.options!!.numberOfQuestions) {
            val intent = Intent(this, GameCompletedActivity::class.java)
            val bundle = Bundle()

            bundle.putParcelable("GAME_MODEL", gameModel)
            intent.putExtra("BUNDLE", bundle)
            startActivityForResult(intent, 6969)
        }
    }

    @SuppressLint("ResourceType")
    private fun manageOptionsState(options: ArrayList<Pareja>) {
        val currentQuestion = gameModel.getCurrentQuestion()
        adapter.clear()
        adapter.addAll(options)
        adapter.notifyDataSetChanged()

        currentQuestion.optionsAnswered.forEachIndexed { index, flag ->
            listViewOptions.getChildAt(index)?.setBackgroundColor(
                when (flag) {
                    true -> {
                        if (currentQuestion.options[index].second) {
                            Color.parseColor(resources.getString(if (currentQuestion.hintsUsed) R.color.black else R.color.green))
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
        val question = gameModel.getCurrentQuestion()
        val options = question.options
        question.hintsUsed = true

        if (gameModel.answerQuestionByHint()) {
            val index = question.options.indexOfFirst { pair -> pair.second }

            question.answered = true
            question.optionsAnswered[index] = true
            listViewOptions.getChildAt(index)
                .setBackgroundColor(Color.parseColor(resources.getString(R.color.black)))

            gameModel.correctAnswers++
            gameModel.questionsAnswered++

            toGameResults()

            return
        }

        val optionAnswered =
            options.filterIndexed { index, option -> !question.optionsAnswered[index] && !option.second }
                .random()
        val index = question.options.indexOf(optionAnswered)

        question.optionsAnswered[index] = true
        listViewOptions.getChildAt(index)
            .setBackgroundColor(Color.parseColor(resources.getString(R.color.red)))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            6969 -> when (resultCode) {
                RESULT_CANCELED -> finish()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putParcelable("LIST_VIEW", listViewOptions.onSaveInstanceState())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        val state = savedInstanceState.getParcelable<Parcelable>("LIST_VIEW") as Parcelable
        listViewOptions.onRestoreInstanceState(state)
    }
}