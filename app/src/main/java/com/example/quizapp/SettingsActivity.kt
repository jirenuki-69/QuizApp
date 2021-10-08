package com.example.quizapp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import androidx.appcompat.widget.SwitchCompat
import com.example.quizapp.Clases.Category
import com.example.quizapp.Clases.Options
import com.google.android.material.slider.Slider
import com.google.android.material.snackbar.Snackbar
import kotlin.random.Random


class SettingsActivity : AppCompatActivity() {
    private lateinit var checkboxTodos: CheckBox
    private lateinit var videoGamesCheckbox: CheckBox
    private lateinit var marioBrosCheckbox: CheckBox
    private lateinit var spiderManCheckbox: CheckBox
    private lateinit var carsCheckbox: CheckBox
    private lateinit var dragonBallCheckbox: CheckBox
    private lateinit var terminalMontageCheckbox: CheckBox
    private lateinit var hintsSwitch: SwitchCompat
    private lateinit var randomButton: Button
    private lateinit var questionsNumberSlider: Slider
    private lateinit var difficultySlider: Slider
    private lateinit var saveButton: Button

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val bundle = intent!!.getBundleExtra("BUNDLE")
        val optionsModel = bundle!!.getParcelable<Options>("OPTIONS_MODEL") as Options

        checkboxTodos = findViewById(R.id.todos_checkbox)
        videoGamesCheckbox = findViewById(R.id.videogames_checkbox)
        marioBrosCheckbox = findViewById(R.id.mario_bros_checkbox)
        spiderManCheckbox = findViewById(R.id.spiderman_checkbox)
        carsCheckbox = findViewById(R.id.cars_checkbox)
        dragonBallCheckbox = findViewById(R.id.dragon_ball_checkbox)
        terminalMontageCheckbox = findViewById(R.id.terminal_montage_checkbox)
        hintsSwitch = findViewById(R.id.pistas_switch)
        randomButton = findViewById(R.id.random_button)
        questionsNumberSlider = findViewById(R.id.slider_numero_de_preguntas)
        difficultySlider = findViewById(R.id.slider_dificultad)
        saveButton = findViewById(R.id.save_button)

        if (optionsModel.categories.size > 0) {
            manageOptionsOnStart(
                optionsModel.categories,
                optionsModel.hintsAvailable,
                optionsModel.numberOfQuestions,
                optionsModel.difficulty
            )
        }

        hintsSwitch.setOnCheckedChangeListener { _, isChecked ->
            optionsModel.hintsAvailable = isChecked
        }

        checkboxTodos.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                checkboxTodos.isEnabled = false
                changeCheckBoxChecked(videoGamesCheckbox)
                changeCheckBoxChecked(marioBrosCheckbox)
                changeCheckBoxChecked(spiderManCheckbox)
                changeCheckBoxChecked(carsCheckbox)
                changeCheckBoxChecked(dragonBallCheckbox)
                changeCheckBoxChecked(terminalMontageCheckbox)
            }
        }

        videoGamesCheckbox.setOnCheckedChangeListener { _, isChecked ->
            changeCheckBoxTodosState()

            when (isChecked) {
                true -> optionsModel.putCategory("video_games")
                false -> optionsModel.removeCategory("video_games")
            }
        }

        marioBrosCheckbox.setOnCheckedChangeListener { _, isChecked ->
            changeCheckBoxTodosState()

            when (isChecked) {
                true -> optionsModel.putCategory("mario_bros")
                false -> optionsModel.removeCategory("mario_bros")
            }
        }

        spiderManCheckbox.setOnCheckedChangeListener { _, isChecked ->
            changeCheckBoxTodosState()

            when (isChecked) {
                true -> optionsModel.putCategory("spider_man")
                false -> optionsModel.removeCategory("spider_man")
            }
        }

        carsCheckbox.setOnCheckedChangeListener { _, isChecked ->
            changeCheckBoxTodosState()

            when (isChecked) {
                true -> optionsModel.putCategory("cars")
                false -> optionsModel.removeCategory("cars")
            }
        }

        dragonBallCheckbox.setOnCheckedChangeListener { _, isChecked ->
            changeCheckBoxTodosState()

            when (isChecked) {
                true -> optionsModel.putCategory("dragon_ball")
                false -> optionsModel.removeCategory("dragon_ball")
            }
        }

        terminalMontageCheckbox.setOnCheckedChangeListener { _, isChecked ->
            changeCheckBoxTodosState()

            when (isChecked) {
                true -> optionsModel.putCategory("terminal_montage")
                false -> optionsModel.removeCategory("terminal_montage")
            }
        }

        randomButton.setOnClickListener {
            randomSettings()
        }

        questionsNumberSlider.addOnChangeListener { _, value, _ ->
            optionsModel.numberOfQuestions = value.toInt()
        }

        difficultySlider.addOnChangeListener { _, value, _ ->
            optionsModel.changeDifficulty(value)
        }

        saveButton.setOnClickListener {
            if (optionsModel.categories.size == 0) {
                val snack = Snackbar.make(this, it,  resources.getString(R.string.no_saving_options), Snackbar.LENGTH_SHORT)
                snack.setBackgroundTint(Color.parseColor(resources.getString(R.color.primary_blue)))
                snack.setTextColor(Color.parseColor(resources.getString(R.color.white)))
                snack.show()

                return@setOnClickListener
            }

            val intent = Intent()
            intent.putExtra("OPTIONS_MODEL", optionsModel)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun manageOptionsOnStart(
        categories: ArrayList<Category>,
        hintsAvailable: Boolean,
        numberOfQuestions: Int,
        difficulty: String
    ) {
        Log.d("QUIZ_APP_DEBUG", "${categories.size}")
        val categoriesNames = categories.map { it.name }
        checkboxTodos.isEnabled = categories.size != 6
        checkboxTodos.isChecked = categories.size == 6
        videoGamesCheckbox.isChecked = categoriesNames.contains("video_games")
        marioBrosCheckbox.isChecked = categoriesNames.contains("mario_bros")
        spiderManCheckbox.isChecked = categoriesNames.contains("spider_man")
        carsCheckbox.isChecked = categoriesNames.contains("cars")
        dragonBallCheckbox.isChecked = categoriesNames.contains("dragon_ball")
        terminalMontageCheckbox.isChecked = categoriesNames.contains("terminal_montage")

        hintsSwitch.isChecked = hintsAvailable
        questionsNumberSlider.value = numberOfQuestions.toFloat()
        difficultySlider.value = when (difficulty) {
            "Fácil" -> 1.0f
            "Medio" -> 2.0f
            "Difícil" -> 3.0f
            else -> 1.0f
        }
    }

    private fun changeCheckBoxChecked(checkBox: CheckBox) {
        checkBox.isChecked = true
    }

    private fun changeCheckBoxTodosState() {
        val checks = arrayOf(
            videoGamesCheckbox.isChecked,
            marioBrosCheckbox.isChecked,
            spiderManCheckbox.isChecked,
            carsCheckbox.isChecked,
            dragonBallCheckbox.isChecked,
            terminalMontageCheckbox.isChecked
        )

        val flag = checks.contains(false)

        checkboxTodos.isEnabled = flag
        checkboxTodos.isChecked = !flag
    }

    private fun randomSettings() {
        videoGamesCheckbox.isChecked = Random.nextBoolean()
        marioBrosCheckbox.isChecked = Random.nextBoolean()
        spiderManCheckbox.isChecked = Random.nextBoolean()
        carsCheckbox.isChecked = Random.nextBoolean()
        dragonBallCheckbox.isChecked = Random.nextBoolean()
        terminalMontageCheckbox.isChecked = Random.nextBoolean()
        hintsSwitch.isChecked = Random.nextBoolean()
        questionsNumberSlider.value = Random.nextInt(
            questionsNumberSlider.valueFrom.toInt(),
            questionsNumberSlider.valueTo.toInt() + 1
        ).toFloat()
        difficultySlider.value = Random.nextInt(
            difficultySlider.valueFrom.toInt(),
            difficultySlider.valueTo.toInt() + 1
        ).toFloat()
    }
}