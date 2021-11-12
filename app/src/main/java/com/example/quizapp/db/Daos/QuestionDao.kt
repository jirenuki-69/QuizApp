package com.example.quizapp.db.Daos

import androidx.room.Dao
import androidx.room.Query
import com.example.quizapp.db.Entities.Question

@Dao
interface QuestionDao {
  @Query("SELECT * FROM questions")
  fun getAll(): List<Question>

  @Query("SELECT * FROM questions WHERE category_id IN (:categoryIds) ORDER BY RANDOM() LIMIT (:numberOfQuestions)")
  fun getCategoriesGameQuestions(categoryIds: List<Int>, numberOfQuestions: Int): List<Question>
}