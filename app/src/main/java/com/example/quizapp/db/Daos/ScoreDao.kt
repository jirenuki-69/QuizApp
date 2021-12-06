package com.example.quizapp.db.Daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.quizapp.Clases.GlobalScore
import com.example.quizapp.db.Entities.Score

@Dao
interface ScoreDao {
    @Query("SELECT * FROM scores ORDER BY date DESC")
    fun getAll(): List<Score>

    @Query("""
        SELECT 
        s.profile_id as profileId, 
        p.name as profileName, 
        SUM(s.total_score) AS score, 
        COUNT(s.id) AS gamesPlayed, 
        COUNT(CASE WHEN s.hints_used = 0 THEN 1 END) AS gamesUsingHint
        FROM scores s JOIN profiles p ON s.profile_id = p.id
        GROUP BY profile_id ORDER BY score DESC
    """)
    fun getGlobalScores(): MutableList<GlobalScore>

    @Query("""
        SELECT 
        s.profile_id as profileId, 
        p.name as profileName, 
        SUM(s.total_score) AS score, 
        COUNT(s.id) AS gamesPlayed, 
        COUNT(CASE WHEN s.hints_used = 0 THEN 1 END) AS gamesUsingHint
        FROM scores s JOIN profiles p ON s.profile_id = p.id
        GROUP BY profile_id ORDER BY gamesPlayed DESC
    """)
    fun getGlobalScoresOrderedByGamesPlayed(): MutableList<GlobalScore>

    @Query("SELECT * FROM scores WHERE profile_id = (:profileId) ORDER BY date DESC")
    fun getProfileScores(profileId: Int): MutableList<Score>

    @Query("SELECT * FROM scores WHERE profile_id = (:profileId) ORDER BY total_score DESC")
    fun getProfileScoresOrderedByScore(profileId: Int): MutableList<Score>

    @Query("SELECT * FROM scores ORDER BY date, total_score DESC, hints_used LIMIT 5")
    fun getBestFive(): MutableList<Score>

    @Query("DELETE FROM scores WHERE profile_id = (:profileId)")
    fun deleteProfileScores(profileId: Int)

    @Insert
    fun insert(score: Score)
}