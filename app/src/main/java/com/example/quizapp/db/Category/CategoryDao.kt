package com.example.quizapp.db.Category

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CategoryDao {
  @Query("SELECT * FROM categories")
  fun getAll() : List<Category>

  @Query("SELECT * FROM categories WHERE id = (:categoryId)")
  fun getCategory(categoryId: Int): Category
}