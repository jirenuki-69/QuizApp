package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {
    private lateinit var playButton: Button
    private lateinit var optionsButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val allQuestions = getAllQuestionsPerCategory(getAllCategoriesQuestions())

        var optionsModel = Options(allQuestions)

        val actionsBar = supportActionBar
        actionsBar!!.title = resources.getString(R.string.home_text)

        playButton = findViewById(R.id.button_play)
        optionsButton = findViewById(R.id.button_options)

        val result = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val resultCode = it.resultCode
            if (resultCode == RESULT_OK) {
                optionsModel = it.data?.getParcelableExtra<Options>("OPTIONS_MODEL")!!
                Log.d("QUIZ_APP_DEBUG", "Nuevo Hash Code: ${optionsModel.hashCode()}")
            }
        }

        playButton.setOnClickListener { _ ->
            val intent = Intent(this, GameActivity::class.java)
            result.launch(intent)
        }

        optionsButton.setOnClickListener { _ ->
            Log.d("QUIZ_APP_DEBUG", "Hash Code en MainActivity: ${optionsModel.hashCode()}")
            val intent = Intent(this, SettingsActivity::class.java)
            intent.putExtra("OPTIONS_MODEL", optionsModel)
            result.launch(intent)
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

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        when (requestCode){
//            5 -> when (resultCode){
//                RESULT_OK ->Toast.makeText(
//                    this,
//                    "OK ${data!!.getStringExtra("CHEAT_TEST_TEXT")}",
//                    Toast.LENGTH_LONG).show()
//            }
//            RESULT_CANCELED -> Toast.makeText(this, "CANCEL",Toast.LENGTH_LONG).show()
//
//        }
//    }
}