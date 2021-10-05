package com.example.quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import com.example.quizapp.Clases.GameModel
import com.example.quizapp.Clases.OptionAdapter

class GameActivity : AppCompatActivity() {
    private lateinit var listViewOptions: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        listViewOptions = findViewById(R.id.list_view)

        val optionsModel = Options(getAllQuestionsPerCategory(getAllCategoriesQuestions()))

        optionsModel.putCategory("mario_bros")
        optionsModel.putCategory("terminal_montage")

        optionsModel.changeDifficulty(2.0f)
        optionsModel.numberOfQuestions = 2
        optionsModel.hintsAvailable = true

        val gameModel = GameModel(optionsModel)

        Log.d("QUIZ_APP_DEBUG", "${gameModel.gameModelQuestions}")

        val options = arrayOf("Opción 1", "Opción 2", "Opción 3", "Opción 4")

        listViewOptions.adapter = OptionAdapter(this, options)

        listViewOptions.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(this, parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show()
        }
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