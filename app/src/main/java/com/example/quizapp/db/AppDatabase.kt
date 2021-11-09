package com.example.quizapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.quizapp.db.Category.Category
import com.example.quizapp.db.Choice.Choice
import com.example.quizapp.db.Game.Game
import com.example.quizapp.db.GameQuestion.GameQuestion
import com.example.quizapp.db.Question.Question
import com.example.quizapp.db.Score.Score
import com.example.quizapp.db.SettingsCategories.SettingsCategories

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
    // TODO: Daos
}