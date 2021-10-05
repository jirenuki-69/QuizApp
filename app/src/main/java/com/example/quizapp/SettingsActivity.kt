package com.example.quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import androidx.appcompat.widget.SwitchCompat
import com.google.android.material.slider.Slider
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        fun changeCheckBoxChecked(checkBox: CheckBox, checked: Boolean) {
            checkBox.isChecked = checked
        }

        fun changeCheckBoxTodosState() {
            val checks = arrayOf<Boolean>(
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

        fun randomSettings() {
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

        val actionsBar = supportActionBar
        actionsBar!!.title = resources.getString(R.string.options_text)

        actionsBar.setDisplayHomeAsUpEnabled(true)

        val optionsModel = Options(this)

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

        hintsSwitch.setOnCheckedChangeListener { _, isChecked ->
            optionsModel.hintsAvailable = isChecked
        }

        checkboxTodos.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                checkboxTodos.isEnabled = false
                changeCheckBoxChecked(videoGamesCheckbox, true)
                changeCheckBoxChecked(marioBrosCheckbox, true)
                changeCheckBoxChecked(spiderManCheckbox, true)
                changeCheckBoxChecked(carsCheckbox, true)
                changeCheckBoxChecked(dragonBallCheckbox, true)
                changeCheckBoxChecked(terminalMontageCheckbox, true)
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

        randomButton.setOnClickListener { _ ->
            randomSettings()
        }

        questionsNumberSlider.addOnChangeListener { _, value, _ ->
            optionsModel.numberOfQuestions = value.toInt()
            Log.d("DEBUG", "${optionsModel.numberOfQuestions}")
        }

        difficultySlider.addOnChangeListener { _, value, _ ->
            optionsModel.changeDifficulty(value)
        }
    }
}