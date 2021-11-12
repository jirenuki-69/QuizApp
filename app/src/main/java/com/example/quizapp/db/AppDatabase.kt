package com.example.quizapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.quizapp.db.Category.Category
import com.example.quizapp.db.Category.CategoryDao
import com.example.quizapp.db.Choice.Choice
import com.example.quizapp.db.Choice.ChoiceDao
import com.example.quizapp.db.Game.Game
import com.example.quizapp.db.Game.GameDao
import com.example.quizapp.db.GameQuestion.GameQuestion
import com.example.quizapp.db.GameQuestion.GameQuestionDao
import com.example.quizapp.db.Question.Question
import com.example.quizapp.db.Question.QuestionDao
import com.example.quizapp.db.Score.Score
import com.example.quizapp.db.Score.ScoreDao
import com.example.quizapp.db.Settings.Settings
import com.example.quizapp.db.Settings.SettingsDao
import com.example.quizapp.db.SettingsCategories.SettingsCategories
import com.example.quizapp.db.SettingsCategories.SettingsCategoriesDao

@Database(
  entities = [
    Category::class,
    Question::class,
    Choice::class,
    Settings::class,
    Score::class,
    Game::class,
    GameQuestion::class,
    SettingsCategories::class
  ],
  version = 1
)
abstract class AppDatabase : RoomDatabase() {
  abstract fun QuestionDao(): QuestionDao
  abstract fun CategoryDao(): CategoryDao
  abstract fun ChoiceDao(): ChoiceDao
  abstract fun SettingsDao(): SettingsDao
  abstract fun GameDao(): GameDao
  abstract fun SettingsCategoriesDao(): SettingsCategoriesDao
  abstract fun ScoreDao(): ScoreDao
  abstract fun GameQuestionDao(): GameQuestionDao

  companion object {
    private var instance: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase {
      if (instance == null) {
        synchronized(this) {
          instance =
            Room.databaseBuilder(context, AppDatabase::class.java, "quote_database")
              .createFromAsset("db/quizapp.db")
              .allowMainThreadQueries()
              .build()
        }
      }
      return instance!!
    }
  }
}