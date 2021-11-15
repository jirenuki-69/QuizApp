package com.example.quizapp.db.Daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.quizapp.db.Entities.GameQuestion
import com.example.quizapp.db.Entities.Question

@Dao
interface GameQuestionDao {
    @Query("SELECT * FROM game_questions WHERE game_id = (:gameId) AND question_number = (:questionNumber)")
    fun getGameQuestion(gameId: Int, questionNumber: Int): GameQuestion

    @Query("SELECT * FROM questions WHERE id = (:questionId)")
    fun getRealQuestion(questionId: Int): Question

    @Query("SELECT * FROM game_questions WHERE game_id = (:gameId) AND is_answered = 1")
    fun getAllQuestionsAnswered(gameId: Int): Array<GameQuestion>

    @Update
    fun update(gameQuestion: GameQuestion)

    @Insert
    fun insertAll(gameQuestions: List<GameQuestion>): List<Long>
}