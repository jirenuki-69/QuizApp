package com.example.quizapp.db.Daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.quizapp.db.Entities.GameChoices

@Dao
interface GameChoicesDao {
    @Query("SELECT * FROM game_choices WHERE game_question_id = (:gameQuestionId) ORDER BY position_number ASC")
    fun getGameChoices(gameQuestionId: Int): List<GameChoices>

    @Query("""
        SELECT choices.text
        FROM choices JOIN game_choices ON choices.id = game_choices.choice_id 
        JOIN game_questions ON game_questions.id = game_choices.game_question_id
        WHERE game_questions.id = (:gameQuestionId)
        ORDER BY game_questions.question_number, game_choices.position_number
    """)
    fun getGameChoicesText(gameQuestionId: Int): List<String>

    @Query("SELECT * FROM game_choices WHERE game_question_id = (:gameQuestionId) AND position_number = (:position)")
    fun getGameChoice(gameQuestionId: Int, position: Int): GameChoices

    @Query("SELECT * FROM game_choices WHERE choice_id = (SELECT id FROM choices WHERE question_id = (:questionId) AND is_correct = 1)")
    fun getCorrectChoice(questionId: Int): GameChoices

    @Query("""
        SELECT gc.* FROM game_choices  gc
        JOIN choices c ON c.id = gc.choice_id
        WHERE gc.game_question_id = (:gameQuestionId) AND gc.is_answered = 0 AND c.is_correct = 0
        ORDER BY RANDOM() LIMIT 1
    """)
    fun getRandomIncorrectChoice(gameQuestionId: Int): GameChoices

    @Query("SELECT * FROM game_choices WHERE game_question_id = (:gameQuestionId) AND is_answered = 1")
    fun getAnsweredChoices(gameQuestionId: Int): List<GameChoices>

    @Update
    fun update(gameChoice: GameChoices)

    @Insert
    fun insert(gameChoices: GameChoices)

    @Insert
    fun insert(vararg gameChoices: GameChoices)

    @Insert
    fun insertAll(gameChoices: List<GameChoices>)
}