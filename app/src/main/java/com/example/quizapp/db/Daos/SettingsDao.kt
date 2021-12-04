package com.example.quizapp.db.Daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.example.quizapp.db.Entities.Settings

@Dao
interface SettingsDao {
  @Query("SELECT * FROM settings WHERE profile_id = (:profileId)")
  fun getProfileSettings(profileId: Int): Settings?

  @Query("SELECT * FROM settings")
  fun getAll() : List<Settings>

  @Query("SELECT * FROM settings LIMIT 1")
  fun getFirstSettings(): Settings

  @Update
  fun update(settings: Settings)
}