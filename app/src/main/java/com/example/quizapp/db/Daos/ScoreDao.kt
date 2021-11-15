package com.example.quizapp.db.Daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.quizapp.db.Entities.Score

@Dao
interface ScoreDao {
    @Query("SELECT * FROM scores")
    fun getAll(): List<Score>

    @Insert
    fun insert(score: Score)
}