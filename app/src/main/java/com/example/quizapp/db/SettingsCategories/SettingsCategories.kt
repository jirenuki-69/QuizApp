package com.example.quizapp.db.SettingsCategories

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import com.example.quizapp.db.Category.Category
import com.example.quizapp.db.Settings.Settings

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
    ],
    primaryKeys = ["category_id", "settings_id"]
)
data class SettingsCategories(
    @ColumnInfo(name = "category_id") val categoryId: Int,
    @ColumnInfo(name = "settings_id") val settingsId: Int,
)