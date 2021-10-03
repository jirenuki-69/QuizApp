package com.example.quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.Switch
import androidx.appcompat.widget.SwitchCompat
import com.google.android.material.slider.Slider
import kotlin.random.Random
import androidx.annotation.NonNull

import com.google.android.material.slider.LabelFormatter
import java.util.*


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
        }

        val actionsBar = supportActionBar
        actionsBar!!.title = resources.getString(R.string.options_text)

        actionsBar.setDisplayHomeAsUpEnabled(true)

        val optionsModel = Options()

        checkboxTodos = findViewById(R.id.todos_checkbox)
        videoGamesCheckbox = findViewById(R.id.videogames_checkbox)
        marioBrosCheckbox = findViewById(R.id.mario_bros_checkbox)
        spiderManCheckbox = findViewById(R.id.spiderman_checkbox)
        carsCheckbox = findViewById(R.id.cars_checkbox)
        dragonBallCheckbox = findViewById(R.id.dragon_ball_checkbox)
        terminalMontageCheckbox = findViewById(R.id.terminal_montage_checkbox)
        hintsSwitch = findViewById(R.id.pistas_switch)
        randomButton = findViewById(R.id.random_button)
        difficultySlider = findViewById(R.id.slider_dificultad)

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

        videoGamesCheckbox.setOnCheckedChangeListener { _, _ ->
            changeCheckBoxTodosState()
        }

        marioBrosCheckbox.setOnCheckedChangeListener { _, _ ->
            changeCheckBoxTodosState()
        }

        spiderManCheckbox.setOnCheckedChangeListener { _, _ ->
            changeCheckBoxTodosState()
        }

        carsCheckbox.setOnCheckedChangeListener { _, _ ->
            changeCheckBoxTodosState()
        }

        dragonBallCheckbox.setOnCheckedChangeListener { _, _ ->
            changeCheckBoxTodosState()
        }

        terminalMontageCheckbox.setOnCheckedChangeListener { _, _ ->
            changeCheckBoxTodosState()
        }

        randomButton.setOnClickListener { _ ->
            randomSettings()
        }

        difficultySlider.addOnChangeListener { slider, value, fromUser ->
            Log.d("DEBUG", "$value")
        }
    }
}