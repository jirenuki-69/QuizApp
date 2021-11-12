package com.example.quizapp.db.Game

import androidx.room.Dao
import androidx.room.Query

@Dao
interface GameDao {
    @Query("SELECT * FROM games WHERE settings_id = (:settingsId)")
    fun getGameBySettingsId(settingsId: Int) : Game?
}