package com.example.quizapp.db.SettingsCategories

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.example.quizapp.db.Settings
import com.example.quizapp.db.Category.Category

@Entity(
    tableName = "settings_categories",
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["category_id"],
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = Settings::class,
            parentColumns = ["id"],
            childColumns = ["settings_id"],
            onDelete = CASCADE
        )
    ]
)
data class SettingsCategories(
    @PrimaryKey val category_id: Int,
    @PrimaryKey val settings_id: Int,
)