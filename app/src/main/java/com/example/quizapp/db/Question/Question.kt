package com.example.quizapp.db.Question

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import com.example.quizapp.db.Category.Category

@Entity(
    tableName = "questions",
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["category_id"],
            onDelete = CASCADE
        )
    ]
)
data class Question(
    @PrimaryKey val id: Int,
    @ColumnInfo val text: String,
    @ColumnInfo(name = "category_id") val categoryId: Int
)