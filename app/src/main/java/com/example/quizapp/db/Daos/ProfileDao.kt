package com.example.quizapp.db.Daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Delete

import com.example.quizapp.db.Entities.Profile

@Dao
interface ProfileDao {
    @Query("SELECT * FROM profiles")
    fun getAllProfiles(): MutableList<Profile>

    @Query("SELECT * FROM profiles WHERE id = (:id)")
    fun getProfile(id: Int): Profile

    @Query("SELECT * FROM profiles WHERE active_session = 1")
    fun getActiveProfile(): Profile?

    @Query("Delete FROM profiles WHERE id = (:id)")
    fun deleteFromId(id: Int)

    @Query("UPDATE profiles SET active_session = 0 WHERE id = (:id)")
    fun setActiveSessionToFalse(id: Int)

    @Insert
    fun insert(profile: Profile): Long

    @Update
    fun update(profile: Profile)

    @Delete
    fun delete(profile: Profile)
}