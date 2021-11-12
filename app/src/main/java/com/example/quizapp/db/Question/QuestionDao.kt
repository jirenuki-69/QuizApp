package com.example.quizapp.db.Question

import androidx.room.Dao
import androidx.room.Query

@Dao
interface QuestionDao {
  @Query("SELECT * FROM questions")
  fun getAll(): List<Question>

  @Query("SELECT * FROM questions WHERE category_id IN (:categoryIds) ORDER BY RANDOM() LIMIT (:numberOfQuestions)")
  fun getCategoriesGameQuestions(categoryIds: List<Int>, numberOfQuestions: Int): List<Question>
}