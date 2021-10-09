package com.example.quizapp.Clases

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

/**
 * Toda la lógica y manejadores de eventos del juego se almacena en esta clase.
 * @property options Los ajustes guardados del juego.
 * @property currentQuestionIndex El índice de la pregunta actual.
 * @property numberOfHintsAvailable El número de pistas disponibles a usar.
 * @property gameModelQuestions Las preguntas y opciones ya revueltas.
 * @property hintsUsed El número de pistas utilizadas por el usuario.
 * @property questionsAnswered El número de preguntas respondidas.
 * @property correctAnswers El número de preguntas respondidas correctamente.
 * @property correctAnswersWithoutHint El número de preguntas respondidas correctamente sin el uso de pistas.
 */

@Parcelize
class GameModel(
    val options: Options?,
    private var currentQuestionIndex: Int = 0,
    var numberOfHintsAvailable: Int = when (options!!.difficulty) {
        "Fácil" -> 5
        "Medio" -> 3
        "Difícil" -> 1
        else -> 0
    },
    private var gameModelQuestions: Array<Question?> = arrayOfNulls(options!!.numberOfQuestions),
    var hintsUsed: Int = 0,
    var questionsAnswered: Int = 0,
    var correctAnswers: Int = 0,
    var correctAnswersWithoutHint: Int = 0,
) : ViewModel(), Parcelable {

    init {
        this.gameModelQuestions = getGameQuestions()
    }

    /**
     * El sistema de puntaje por pregunta correcta. Fácil: 1, Medio: 2, Difícil: 4.
     */

    @IgnoredOnParcel
    private val pointsCriteria = when (options!!.difficulty) {
        "Fácil" -> 1
        "Medio" -> 2
        "Difícil" -> 4
        else -> 0
    }

    /**
     * Una función que recolecta todas las preguntas que dependerán de las categorías seleccionadas en los ajustes.
     * Las preguntas y las opciones se revuelven de manera aleatoria.
     * @return devuelve un [Array] de [Question].
     */

    private fun getGameQuestions(): Array<Question?> {
        val allGameQuestions = arrayOfNulls<Question>(options!!.numberOfQuestions)
        val arrayTemp = arrayListOf<Question>()

        options.categories.forEach { category ->
            category.questions.forEach { question -> arrayTemp.add(question) }
        }

        arrayTemp.shuffled().forEachIndexed { index, question ->
            if (index < options.numberOfQuestions) {
                val alteredOptions = shuffleQuestionOptionsByDifficulty(question.options)
                allGameQuestions[index] = Question(
                    question.text,
                    alteredOptions
                )
                allGameQuestions[index]!!.optionsAnswered = alteredOptions.map { false }
                    .toCollection(ArrayList())
            } else {
                return@forEachIndexed
            }
        }

        return allGameQuestions
    }

    /**
     * Revuelve las opciones de acuerdo a la dificultad.
     * @param questionOptions Las opciones de la pregunta a revolver.
     * @return Un [ArrayList] de [Pareja] arreglo de opciones revueltas.
     */

    private fun shuffleQuestionOptionsByDifficulty(questionOptions: ArrayList<Pareja>): ArrayList<Pareja> {
        when (options!!.difficulty) {
            "Fácil" -> {
                val correctAnswer = questionOptions.removeAt(0)
                val incorrectAnswer = questionOptions.random()

                return arrayListOf(correctAnswer, incorrectAnswer).shuffled()
                    .toCollection(ArrayList())
            }
            "Medio" -> {
                val correctAnswer = questionOptions.removeAt(0)
                val incorrectAnswer = questionOptions.random()

                questionOptions.remove(incorrectAnswer)

                val incorrectAnswer2 = questionOptions.random()

                return arrayListOf(correctAnswer, incorrectAnswer, incorrectAnswer2).shuffled()
                    .toCollection(ArrayList())

            }
            "Difícil" -> return questionOptions.shuffled().toCollection(ArrayList())
        }

        return arrayListOf()
    }

    /**
     * Devuelve el desempeño del jugador dependiendo del puntaje total.
     * @return Un [String] del desempeño.
     */

    fun getGameAverage(): String {
        return when ((getUserFinalScore().toDouble() / getGameTotalScore()) * 100) {
            in 0.0..49.0 -> "bad_score"
            in 50.0..90.0 -> "medium_score"
            100.0 -> "perfect_score"
            else -> "bad_score"
        }
    }

    /**
     * Devuelve el puntaje total posible que se pudo haber sacado en el juego.
     * @return El puntaje total del juego como [Int].
     */

    fun getGameTotalScore(): Int {
        return options!!.numberOfQuestions * pointsCriteria
    }

    /**
     * Devuelve el puntaje total del jugador, sin penalización por uso de pistas.
     * @return El puntaje total del jugador como [Int].
     */

    fun getUserTotalScore(): Int {
        return correctAnswers * pointsCriteria
    }

    /**
     * Devuelve los puntos de penalización por el uso de pistas.
     * @return Los puntos de penalización como [Int].
     */

    fun getHintsScorePenalization(): Int {
        return hintsUsed * 2
    }

    /**
     * Devuelve el puntaje final del usuario, aplicando la penalización por uso de pistas.
     * @return El puntaje final del usuario, si el resultado es menos de 0, retorna 0.
     */

    fun getUserFinalScore(): Int {
        val finalScore = getUserTotalScore() - getHintsScorePenalization()

        if (finalScore < 0) {
            return 0
        }

        return finalScore
    }

    /**
     * Sirve para verificar si la pregunta puede ser respondida por medio de una pista.
     * @return Un [Boolean] indicando si puede ser respondida por pistas.
     */

    fun answerQuestionByHint(): Boolean {
        var count = 0

        getCurrentQuestion().optionsAnswered.forEach {
            if (!it) {
                count++
            }
        }

        return count == 2
    }

    /**
     * Devuelve la pregunta actual.
     * @return La pregunta actual como [Int].
     */

    fun getCurrentQuestionNumber(): Int {
        return currentQuestionIndex + 1
    }

    /**
     * Devuelve el objeto de la pregunta actual.
     * @return La pregunta actual como objeto [Question].
     */

    fun getCurrentQuestion(): Question {
        return gameModelQuestions[currentQuestionIndex]!!
    }

    /**
     * Settea la pregunta actual a la anterior.
     * @return Un objeto [Question] que es la pregunta anterior.
     */

    fun previousQuestion(): Question {
        val size = gameModelQuestions.size - 1
        currentQuestionIndex =
            size - (if (currentQuestionIndex > 0) gameModelQuestions.size - currentQuestionIndex else 0)

        return gameModelQuestions[currentQuestionIndex]!!
    }

    /**
     * Settea la pregunta actual a la siguiente.
     * @return Un objeto [Question] que es la pregunta siguiente.
     */

    fun nextQuestion(): Question {
        currentQuestionIndex = (currentQuestionIndex + 1) % gameModelQuestions.size

        return gameModelQuestions[currentQuestionIndex]!!
    }
}