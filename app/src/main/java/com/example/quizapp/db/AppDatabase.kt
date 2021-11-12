package com.example.quizapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.quizapp.db.Entities.Category
import com.example.quizapp.db.Daos.CategoryDao
import com.example.quizapp.db.Entities.Choice
import com.example.quizapp.db.Daos.ChoiceDao
import com.example.quizapp.db.Entities.Game
import com.example.quizapp.db.Daos.GameDao
import com.example.quizapp.db.Entities.GameQuestion
import com.example.quizapp.db.Daos.GameQuestionDao
import com.example.quizapp.db.Entities.Question
import com.example.quizapp.db.Daos.QuestionDao
import com.example.quizapp.db.Entities.Score
import com.example.quizapp.db.Daos.ScoreDao
import com.example.quizapp.db.Entities.Settings
import com.example.quizapp.db.Daos.SettingsDao
import com.example.quizapp.db.Entities.SettingsCategories
import com.example.quizapp.db.Daos.SettingsCategoriesDao

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