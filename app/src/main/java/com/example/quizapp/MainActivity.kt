package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.example.quizapp.Clases.Options
import com.example.quizapp.Clases.Pareja
import com.example.quizapp.Clases.Question
import com.example.quizapp.Clases.viewModelFactory

class MainActivity : AppCompatActivity() {
    /**
     * * Views declaration
     */
    private lateinit var playButton: Button
    private lateinit var optionsButton: Button
    private lateinit var scoreButton: Button
    private lateinit var optionsModel: Options

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * * Views init
         */

        playButton = findViewById(R.id.button_play)
        optionsButton = findViewById(R.id.button_options)
        scoreButton = findViewById(R.id.score_button)

        val allQuestions = getAllQuestionsPerCategory(getAllCategoriesQuestions())

        /**
         * * Creación de un modelo de opciones
         */

        optionsModel = ViewModelProvider(
            this,
            viewModelFactory { Options(allQuestions) }
        )[Options::class.java]

        /**
         * * Asignar categorías por default a las opciones
         */

        optionsModel.putCategory("terminal_montage")
        optionsModel.putCategory("dragon_ball")

        /**
         * * Sirve para recibir los resultados de otros activities
         */

        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                optionsModel = it.data!!.getParcelableExtra("OPTIONS_MODEL")!!
                Toast.makeText(
                    this,
                    resources.getString(R.string.saving_options),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        /**
         * * Listeners
         */

        playButton.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            val bundle = Bundle()

            bundle.putParcelable("OPTIONS_MODEL", optionsModel)
            intent.putExtra("BUNDLE", bundle)
            resultLauncher.launch(intent)
        }

        optionsButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            val bundle = Bundle()

            bundle.putParcelable("OPTIONS_MODEL", optionsModel)
            intent.putExtra("BUNDLE", bundle)
            resultLauncher.launch(intent)
        }

        scoreButton.setOnClickListener {
            // Aquí te lleva a la pantalla de puntuaciones
            val intent = Intent(this, ScoreActivity::class.java)

            resultLauncher.launch(intent)
        }
    }

    /**
     * Consigue todas las preguntas de todas las categorías de la aplicación.
     * @return Devuelve un [ArrayList] que actúa como matriz que contiene todas las preguntas
     * separadas por categoría.
     */

    private fun getAllCategoriesQuestions(): ArrayList<Array<String>> {
        val array = ArrayList<Array<String>>()

        array.add(resources.getStringArray(R.array.video_game_history_questions))
        array.add(resources.getStringArray(R.array.mario_bros_questions))
        array.add(resources.getStringArray(R.array.spiderman_questions))
        array.add(resources.getStringArray(R.array.cars_questions))
        array.add(resources.getStringArray(R.array.dragon_ball_questions))
        array.add(resources.getStringArray(R.array.terminal_montage_questions))

        return array
    }

    /**
     * Crea los objetos de tipo [Question] en base a las preguntas recibidas por categoría.
     * @param array Las preguntas separadas por categoría.
     * @return Devuelve un [Array] que contiene todos los objetos [Question] que actúan como todas
     * las preguntas de la categoría.
     */

    private fun getAllQuestionsPerCategory(array: ArrayList<Array<String>>): Array<ArrayList<Question>?> {
        val res = arrayOfNulls<ArrayList<Question>>(6)
        array.forEachIndexed { index, subArray ->
            res[index] = arrayListOf()
            when (index) {
                0 -> {

                    subArray.forEachIndexed { subIndex, element ->
                        val stringResource = "video_game_history_question_${subIndex + 1}_options"
                        val id = resources.getIdentifier(stringResource, "array", packageName)
                        val options = resources.getStringArray(id)

                        val optionsObj = arrayListOf<Pareja>()

                        optionsObj.add(Pareja(options[0], true))
                        optionsObj.add(Pareja(options[1], false))
                        optionsObj.add(Pareja(options[2], false))
                        optionsObj.add(Pareja(options[3], false))

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

                        val optionsObj = arrayListOf<Pareja>()

                        optionsObj.add(Pareja(options[0], true))
                        optionsObj.add(Pareja(options[1], false))
                        optionsObj.add(Pareja(options[2], false))
                        optionsObj.add(Pareja(options[3], false))

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

                        val optionsObj = arrayListOf<Pareja>()

                        optionsObj.add(Pareja(options[0], true))
                        optionsObj.add(Pareja(options[1], false))
                        optionsObj.add(Pareja(options[2], false))
                        optionsObj.add(Pareja(options[3], false))

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

                        val optionsObj = arrayListOf<Pareja>()

                        optionsObj.add(Pareja(options[0], true))
                        optionsObj.add(Pareja(options[1], false))
                        optionsObj.add(Pareja(options[2], false))
                        optionsObj.add(Pareja(options[3], false))

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

                        val optionsObj = arrayListOf<Pareja>()

                        optionsObj.add(Pareja(options[0], true))
                        optionsObj.add(Pareja(options[1], false))
                        optionsObj.add(Pareja(options[2], false))
                        optionsObj.add(Pareja(options[3], false))

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

                        val optionsObj = arrayListOf<Pareja>()

                        optionsObj.add(Pareja(options[0], true))
                        optionsObj.add(Pareja(options[1], false))
                        optionsObj.add(Pareja(options[2], false))
                        optionsObj.add(Pareja(options[3], false))

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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putParcelable("OPTIONS_STATE", optionsModel)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        optionsModel = savedInstanceState.getParcelable<Options>("OPTIONS_STATE") as Options
    }
}