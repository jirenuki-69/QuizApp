package com.example.quizapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.quizapp.db.Daos.*
import com.example.quizapp.db.Entities.*

@Database(
  entities = [
    Category::class,
    Question::class,
    Choice::class,
    Settings::class,
    Score::class,
    Game::class,
    GameQuestion::class,
    SettingsCategories::class,
    GameChoices::class,
    Profile::class
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
  abstract fun GameChoicesDao(): GameChoicesDao
  abstract fun ProfileDao(): ProfileDao

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