package com.example.quizapp

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SwitchCompat
import com.example.quizapp.db.AppDatabase
import com.example.quizapp.db.Entities.Category
import com.example.quizapp.db.Daos.CategoryDao
import com.example.quizapp.db.Daos.ProfileDao
import com.example.quizapp.db.Entities.Settings
import com.example.quizapp.db.Daos.SettingsDao
import com.example.quizapp.db.Entities.SettingsCategories
import com.example.quizapp.db.Daos.SettingsCategoriesDao
import com.example.quizapp.db.Entities.Profile
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
    private lateinit var saveButton: Button
    private lateinit var db: AppDatabase
    private lateinit var profile: Profile
    private lateinit var settings: Settings
    private lateinit var profileDao: ProfileDao
    private lateinit var categoriesDao: CategoryDao
    private lateinit var settingsDao: SettingsDao
    private lateinit var settingsCategoriesDao: SettingsCategoriesDao
    private lateinit var selectedCategories: MutableList<Category>

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        supportActionBar?.hide()

        db = AppDatabase.getInstance(this as Context)

        profileDao = db.ProfileDao()
        categoriesDao = db.CategoryDao()
        settingsDao = db.SettingsDao()
        settingsCategoriesDao = db.SettingsCategoriesDao()

        profile = profileDao.getActiveProfile()!!
        settings = settingsDao.getProfileSettings(profile.id)
        selectedCategories = settingsCategoriesDao.getCategories(settings.id)

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

        manageOptionsOnStart(settings, selectedCategories)

        checkBoxListeners()
        buttonsListeners()
        slidersListeners()

        hintsSwitch.setOnCheckedChangeListener { _, isChecked ->
            settings.hintsEnabled = isChecked
        }
    }

    private fun manageOptionsOnStart(settings: Settings, categories: List<Category>) {
        val categoriesIds = categories.map { it.id }
        checkboxTodos.isEnabled = categories.size != 6
        checkboxTodos.isChecked = categories.size == 6
        videoGamesCheckbox.isChecked = categoriesIds.contains(17)
        marioBrosCheckbox.isChecked = categoriesIds.contains(18)
        spiderManCheckbox.isChecked = categoriesIds.contains(19)
        carsCheckbox.isChecked = categoriesIds.contains(20)
        dragonBallCheckbox.isChecked = categoriesIds.contains(21)
        terminalMontageCheckbox.isChecked = categoriesIds.contains(22)

        hintsSwitch.isChecked = settings.hintsEnabled
        questionsNumberSlider.value = settings.numberOfQuestions.toFloat()
        difficultySlider.value = settings.getDifficultyNumber()
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
        val categories = categoriesDao.getAll()

        val isVideoGamesChecked = Random.nextBoolean()
        videoGamesCheckbox.isChecked = isVideoGamesChecked
        manageCategory(categories.find { it.id == 17 }!!, isVideoGamesChecked)

        val isMarioBrosChecked = Random.nextBoolean()
        marioBrosCheckbox.isChecked = isMarioBrosChecked
        manageCategory(categories.find { it.id == 18 }!!, isMarioBrosChecked)

        val isSpiderManChecked = Random.nextBoolean()
        spiderManCheckbox.isChecked = isSpiderManChecked
        manageCategory(categories.find { it.id == 19 }!!, isSpiderManChecked)

        val isCarsChecked = Random.nextBoolean()
        carsCheckbox.isChecked = isCarsChecked
        manageCategory(categories.find { it.id == 20 }!!, isCarsChecked)

        val isDragonBallChecked = Random.nextBoolean()
        dragonBallCheckbox.isChecked = isDragonBallChecked
        manageCategory(categories.find { it.id == 21 }!!, isDragonBallChecked)

        val isTerminalMontageChecked = Random.nextBoolean()
        terminalMontageCheckbox.isChecked = isTerminalMontageChecked
        manageCategory(categories.find { it.id == 22 }!!, isTerminalMontageChecked)

        val hintsEnabled = Random.nextBoolean()
        hintsSwitch.isChecked = hintsEnabled
        settings.hintsEnabled = hintsEnabled

        val numberOfQuestions = Random.nextInt(
            questionsNumberSlider.valueFrom.toInt(),
            questionsNumberSlider.valueTo.toInt() + 1
        ).toFloat()
        questionsNumberSlider.value = numberOfQuestions
        settings.numberOfQuestions = numberOfQuestions.toInt()

        val difficulty = Random.nextInt(
            difficultySlider.valueFrom.toInt(),
            difficultySlider.valueTo.toInt() + 1
        ).toFloat()
        difficultySlider.value = difficulty
        settings.setDifficulty(difficulty)
    }

    private fun slidersListeners() {
        questionsNumberSlider.addOnChangeListener { _, value, _ ->
            settings.numberOfQuestions = value.toInt()
        }

        difficultySlider.addOnChangeListener { _, value, _ ->
            settings.setDifficulty(value)
        }
    }

    private fun buttonsListeners() {
        saveButton.setOnClickListener {
            if (selectedCategories.isEmpty()) {
                Toast.makeText(
                    this,
                    resources.getString(R.string.no_saving_options),
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            dialog()
        }

        randomButton.setOnClickListener {
            randomSettings()
        }
    }

    private fun dialog() {
        val currentGame = db.GameDao().getProfileActiveGame(profile.id)

        if (currentGame != null) {
            val builder = AlertDialog.Builder(this as Context)
            builder.setTitle(resources.getString(R.string.current_game_text))
            builder.setMessage(resources.getString(R.string.update_settings_warning))
            builder.setPositiveButton(resources.getString(R.string.save_text)) { _, _ ->
                db.GameDao().deleteProfileCurrentGame(settings.id)
                updateAndBack()
            }
            builder.setNegativeButton(resources.getString(R.string.cancel_text)) { _, _ ->
                setResult(RESULT_CANCELED)
                finish()
            }

            builder.show()
        } else {
            updateAndBack()
        }
    }

    private fun updateAndBack() {
        updateSettings()
        setResult(RESULT_OK)
        finish()
    }

    private fun updateSettings() {
        settingsDao.update(settings)
        settingsCategoriesDao.clearDataBySettingsId(settings.id)

        selectedCategories.forEach {
            val settingsCategory = SettingsCategories(it.id, settings.id)
            settingsCategoriesDao.insert(settingsCategory)
        }
    }

    private fun manageCategory(category: Category, isChecked: Boolean) {
        when (isChecked) {
            true -> {
                if (!selectedCategories.contains(category)) {
                    selectedCategories.add(category)
                }
            }
            false -> selectedCategories.remove(category)
        }
    }

    private fun checkBoxListeners() {
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

            val category = categoriesDao.getCategory(17)
            manageCategory(category, isChecked)
        }

        marioBrosCheckbox.setOnCheckedChangeListener { _, isChecked ->
            changeCheckBoxTodosState()

            val category = categoriesDao.getCategory(18)
            manageCategory(category, isChecked)
        }

        spiderManCheckbox.setOnCheckedChangeListener { _, isChecked ->
            changeCheckBoxTodosState()

            val category = categoriesDao.getCategory(19)
            manageCategory(category, isChecked)
        }

        carsCheckbox.setOnCheckedChangeListener { _, isChecked ->
            changeCheckBoxTodosState()

            val category = categoriesDao.getCategory(20)
            manageCategory(category, isChecked)
        }

        dragonBallCheckbox.setOnCheckedChangeListener { _, isChecked ->
            changeCheckBoxTodosState()

            val category = categoriesDao.getCategory(21)
            manageCategory(category, isChecked)
        }

        terminalMontageCheckbox.setOnCheckedChangeListener { _, isChecked ->
            changeCheckBoxTodosState()

            val category = categoriesDao.getCategory(22)
            manageCategory(category, isChecked)
        }
    }
}