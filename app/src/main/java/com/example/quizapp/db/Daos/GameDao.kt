package com.example.quizapp.db.Daos

import androidx.room.*
import com.example.quizapp.db.Entities.Game
import com.example.quizapp.db.Entities.Settings

@Dao
interface GameDao {
    @Query("SELECT * FROM games WHERE settings_id = (:settingsId)")
    fun getGameBySettingsId(settingsId: Int): Game?

    @Query("SELECT * FROM settings WHERE id = (SELECT settings_id FROM games WHERE id = (:gameId))")
    fun getSettings(gameId: Int): Settings

    @Query("DELETE FROM games WHERE settings_id = (:settingsId)")
    fun deleteBySettingsId(settingsId: Int)

    @Insert
    fun insert(game: Game): Long

    @Update
    fun update(game:Game)

    @Delete
    fun delete(game:Game)
}