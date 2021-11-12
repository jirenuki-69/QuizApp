package com.example.quizapp.db.Daos

import androidx.room.Dao
import androidx.room.Query
import com.example.quizapp.db.Entities.Game

@Dao
interface GameDao {
    @Query("SELECT * FROM games WHERE settings_id = (:settingsId)")
    fun getGameBySettingsId(settingsId: Int) : Game?
}