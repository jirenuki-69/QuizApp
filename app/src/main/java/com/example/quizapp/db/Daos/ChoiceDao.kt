package com.example.quizapp.db.Daos

import androidx.room.Dao
import androidx.room.Query
import com.example.quizapp.db.Entities.Choice

@Dao
interface ChoiceDao {
    @Query("SELECT * FROM choices WHERE id = (:choiceId)")
    fun getChoice(choiceId: Int) : Choice

    @Query("SELECT text FROM choices WHERE question_id = (:questionId)")
    fun getQuestionChoicesTexts(questionId: Int): List<String>

    @Query("SELECT * FROM choices WHERE question_id = (:questionId)")
    fun getChoices(questionId: Int): MutableList<Choice>
}