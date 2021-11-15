package com.example.quizapp.db.Daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.quizapp.db.Entities.Score

@Dao
interface ScoreDao {
    @Query("SELECT * FROM scores ORDER BY date DESC")
    fun getAll(): List<Score>

    @Query("SELECT * FROM scores ORDER BY date, total_score DESC, hints_enabled LIMIT 5")
    fun getBestFive(): List<Score>

    @Insert
    fun insert(score: Score)
}