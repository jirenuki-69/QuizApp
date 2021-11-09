package com.example.quizapp.db.Game

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.example.quizapp.db.Settings

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Settings::class,
            parentColumns = ["id"],
            childColumns = ["settings_id"],
            onDelete = CASCADE,
            onUpdate = CASCADE
        )
    ]
)
data class Game(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "settings_id") val settingsId: Int,
    @ColumnInfo(name = "current_score") val currentScore: Int
)
