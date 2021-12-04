package com.example.quizapp.db.Daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Delete

import com.example.quizapp.db.Entities.Profile

@Dao
interface ProfileDao {
    @Query("SELECT * FROM profiles WHERE active_session = 1")
    fun getActiveProfile(): Profile?

    @Insert
    fun insert(profile: Profile): Long

    @Update
    fun update(profile: Profile)

    @Delete
    fun delete(profile: Profile)
}