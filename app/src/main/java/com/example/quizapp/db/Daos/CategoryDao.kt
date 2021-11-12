package com.example.quizapp.db.Daos

import androidx.room.Dao
import androidx.room.Query
import com.example.quizapp.db.Entities.Category

@Dao
interface CategoryDao {
  @Query("SELECT * FROM categories")
  fun getAll() : List<Category>

  @Query("SELECT * FROM categories WHERE id = (:categoryId)")
  fun getCategory(categoryId: Int): Category
}