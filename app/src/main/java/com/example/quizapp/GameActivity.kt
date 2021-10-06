package com.example.quizapp

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.example.quizapp.Clases.GameModel
import com.example.quizapp.Clases.OptionAdapter
import com.google.android.material.snackbar.Snackbar
import org.w3c.dom.Text

class GameActivity : AppCompatActivity() {
    private lateinit var listViewOptions: ListView
    private lateinit var questionText: TextView
    private lateinit var nextButton: Button
    private lateinit var previousButton: Button
    private lateinit var questionCounter: TextView

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        questionText = findViewById(R.id.question_text)
        listViewOptions = findViewById(R.id.list_view)
        nextButton = findViewById(R.id.next_button)
        previousButton = findViewById(R.id.prev_button)
        questionCounter = findViewById(R.id.numero_de_preguntas)

        val optionsModel = Options(getAllQuestionsPerCategory(getAllCategoriesQuestions()))

        optionsModel.putCategory("mario_bros")
        optionsModel.putCategory("terminal_montage")
        optionsModel.putCategory("dragon_ball")

        optionsModel.changeDifficulty(2.0f)
        optionsModel.numberOfQuestions = 10
        optionsModel.hintsAvailable = true

        val gameModel = GameModel(optionsModel)

        questionText.text = gameModel.getCurrentQuestion().text

        val firstOptions = gameModel.gameModelQuestions[0]?.options!!.map { option -> option }
            .toCollection(ArrayList())

        val adapter = OptionAdapter(this, firstOptions)

        listViewOptions.adapter = adapter

        listViewOptions.setOnItemClickListener { parent, view, position, _ ->
            val isCorrect = adapter.getItem(position)!!.second
            view.setBackgroundColor(
                if (isCorrect) {
                    Color.parseColor(resources.getString(R.color.green))
                } else {
                    Color.parseColor(resources.getString(R.color.red))
                }
            )

            val snackText = if (isCorrect) resources.getString(R.string.correct_text) else resources.getString(R.string.incorrect_text)
            val snack = Snackbar.make(this, view, snackText, Snackbar.LENGTH_SHORT)
            snack.setBackgroundTint(Color.parseColor(resources.getString(R.color.primary_blue)))
            snack.setTextColor(Color.parseColor(resources.getString(R.color.white)))
            snack.show()
        }

        fun updateQuestionValues(command: String) {
            when (command) {
                "PREVIOUS" -> questionText.text = gameModel.previousQuestion().text
                "NEXT" -> questionText.text = gameModel.nextQuestion().text
            }

            val options = gameModel.getCurrentQuestion().options.map { option -> option }
            adapter.clear()

            for (option in options) {
                adapter.add(option)
            }

            updateNumberCounter(
                gameModel.getCurrentQuestionNumber(),
                gameModel.options.numberOfQuestions
            )
        }

        previousButton.setOnClickListener { updateQuestionValues("PREVIOUS") }

        nextButton.setOnClickListener { updateQuestionValues("NEXT") }
    }

    private fun updateNumberCounter(currentQuestion: Int, totalOfQuestions: Int) {
        questionCounter.text = "$currentQuestion/$totalOfQuestions"
    }

    private fun getAllCategoriesQuestions(): ArrayList<Array<String>> {
        val array = ArrayList<Array<String>>();

        array.add(resources.getStringArray(R.array.video_game_history_questions))
        array.add(resources.getStringArray(R.array.mario_bros_questions))
        array.add(resources.getStringArray(R.array.spiderman_questions))
        array.add(resources.getStringArray(R.array.cars_questions))
        array.add(resources.getStringArray(R.array.dragon_ball_questions))
        array.add(resources.getStringArray(R.array.terminal_montage_questions))

        return array
    }

    private fun getAllQuestionsPerCategory(array: ArrayList<Array<String>>): Array<ArrayList<Question>?> {
        val res = arrayOfNulls<ArrayList<Question>>(6)
        array.forEachIndexed { index, subArray ->
            res[index] = arrayListOf<Question>()
            when (index) {
                0 -> {

                    subArray.forEachIndexed { subIndex, element ->
                        val stringResource = "video_game_history_question_${subIndex + 1}_options"
                        val id = resources.getIdentifier(stringResource, "array", packageName)
                        val options = resources.getStringArray(id)

                        val optionsObj = arrayListOf<Pair<String, Boolean>>()

                        optionsObj.add(Pair(options[0], true))
                        optionsObj.add(Pair(options[1], false))
                        optionsObj.add(Pair(options[2], false))
                        optionsObj.add(Pair(options[3], false))

                        val question = Question(
                            element,
                            optionsObj
                        )

                        res[index]!!.add(question)
                    }
                }
                1 -> {
                    subArray.forEachIndexed { subIndex, element ->
                        val stringResource = "mario_bros_question_${subIndex + 1}_options"
                        val id = resources.getIdentifier(stringResource, "array", packageName)
                        val options = resources.getStringArray(id)

                        val optionsObj = arrayListOf<Pair<String, Boolean>>()

                        optionsObj.add(Pair(options[0], true))
                        optionsObj.add(Pair(options[1], false))
                        optionsObj.add(Pair(options[2], false))
                        optionsObj.add(Pair(options[3], false))

                        val question = Question(
                            element,
                            optionsObj
                        )

                        res[index]!!.add(question)
                    }
                }
                2 -> {
                    subArray.forEachIndexed { subIndex, element ->
                        val stringResource = "spider_man_question_${subIndex + 1}_options"
                        val id = resources.getIdentifier(stringResource, "array", packageName)
                        val options = resources.getStringArray(id)

                        val optionsObj = arrayListOf<Pair<String, Boolean>>()

                        optionsObj.add(Pair(options[0], true))
                        optionsObj.add(Pair(options[1], false))
                        optionsObj.add(Pair(options[2], false))
                        optionsObj.add(Pair(options[3], false))

                        val question = Question(
                            element,
                            optionsObj
                        )

                        res[index]!!.add(question)
                    }
                }
                3 -> {
                    subArray.forEachIndexed { subIndex, element ->
                        val stringResource = "cars_question_${subIndex + 1}_options"
                        val id = resources.getIdentifier(stringResource, "array", packageName)
                        val options = resources.getStringArray(id)

                        val optionsObj = arrayListOf<Pair<String, Boolean>>()

                        optionsObj.add(Pair(options[0], true))
                        optionsObj.add(Pair(options[1], false))
                        optionsObj.add(Pair(options[2], false))
                        optionsObj.add(Pair(options[3], false))

                        val question = Question(
                            element,
                            optionsObj
                        )

                        res[index]!!.add(question)
                    }
                }
                4 -> {
                    subArray.forEachIndexed { subIndex, element ->
                        val stringResource = "dragon_ball_question_${subIndex + 1}_options"
                        val id = resources.getIdentifier(stringResource, "array", packageName)
                        val options = resources.getStringArray(id)

                        val optionsObj = arrayListOf<Pair<String, Boolean>>()

                        optionsObj.add(Pair(options[0], true))
                        optionsObj.add(Pair(options[1], false))
                        optionsObj.add(Pair(options[2], false))
                        optionsObj.add(Pair(options[3], false))

                        val question = Question(
                            element,
                            optionsObj
                        )

                        res[index]!!.add(question)
                    }
                }
                5 -> {
                    subArray.forEachIndexed { subIndex, element ->
                        val stringResource = "terminal_montage_question_${subIndex + 1}_options"
                        val id = resources.getIdentifier(stringResource, "array", packageName)
                        val options = resources.getStringArray(id)

                        val optionsObj = arrayListOf<Pair<String, Boolean>>()

                        optionsObj.add(Pair(options[0], true))
                        optionsObj.add(Pair(options[1], false))
                        optionsObj.add(Pair(options[2], false))
                        optionsObj.add(Pair(options[3], false))

                        val question = Question(
                            element,
                            optionsObj
                        )

                        res[index]!!.add(question)
                    }
                }
            }
        }

        return res
    }

}