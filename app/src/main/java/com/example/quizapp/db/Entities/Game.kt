package com.example.quizapp.db.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "games",
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
) {
    override fun toString(): String {
        return """
            {
                id: $id
                settings_id: $settingsId,
                score: $currentScore
            }
        """.trimIndent()
    }
}
