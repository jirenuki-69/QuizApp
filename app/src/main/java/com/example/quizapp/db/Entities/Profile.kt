package com.example.quizapp.db.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profiles")
data class Profile(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo var name: String,
    @ColumnInfo(name = "active_session") var activeSession: Boolean
)