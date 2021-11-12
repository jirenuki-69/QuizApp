package com.example.quizapp.db.Settings

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update

@Dao
interface SettingsDao {
  @Query("SELECT * FROM settings")
  fun getAll() : List<Settings>

  @Query("SELECT * FROM settings LIMIT 1")
  fun getFirstSettings(): Settings

  @Update
  fun update(settings: Settings)
}