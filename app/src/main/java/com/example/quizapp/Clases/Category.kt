package com.example.quizapp

import android.content.Context

class Category(private val context: Context) {
    private val questions = arrayListOf<Question>()
    private var name = ""

    private val videoGamesQuestions = context.resources.getStringArray(R.array.video_game_history_questions)
    private val marioBrosQuestions = context.resources.getStringArray(R.array.mario_bros_questions)
    private val spiderManQuestions = context.resources.getStringArray(R.array.spiderman_questions)
    private val carsQuestions = context.resources.getStringArray(R.array.cars_questions)
    private val dragonBallQuestions = context.resources.getStringArray(R.array.dragon_ball_questions)
    private val terminalMontageQuestions = context.resources.getStringArray(R.array.terminal_montage_questions)

    fun getName(): String {
        return name
    }

    fun setName(name: String) {
        this.name = name
        when (name) {
            "video_games" -> {
                videoGamesQuestions.forEachIndexed { index, element ->
                    val stringResource = "video_game_history_question_${index + 1}_options"
                    val id = context.resources.getIdentifier(
                        stringResource,
                        "array",
                        context.packageName
                    )
                    val options = context.resources.getStringArray(id)
                    val optionsObj = arrayListOf<Pair<String, Boolean>>()

                    optionsObj.add(Pair(options[0], true))
                    optionsObj.add(Pair(options[1], false))
                    optionsObj.add(Pair(options[2], false))
                    optionsObj.add(Pair(options[3], false))

                    val question = Question(
                        element,
                        optionsObj
                    )

                    questions.add(question)
                }
            }
            "mario_bros" -> {
                marioBrosQuestions.forEachIndexed { index, element ->
                    val stringResource = "mario_bros_question_${index + 1}_options"
                    val id = context.resources.getIdentifier(
                        stringResource,
                        "array",
                        context.packageName
                    )
                    val options = context.resources.getStringArray(id)
                    val optionsObj = arrayListOf<Pair<String, Boolean>>()

                    optionsObj.add(Pair(options[0], true))
                    optionsObj.add(Pair(options[1], false))
                    optionsObj.add(Pair(options[2], false))
                    optionsObj.add(Pair(options[3], false))

                    val question = Question(
                        element,
                        optionsObj
                    )

                    questions.add(question)
                }
            }
            "spider_man" -> {
                spiderManQuestions.forEachIndexed { index, element ->
                    val stringResource = "spider_man_question_${index + 1}_options"
                    val id = context.resources.getIdentifier(
                        stringResource,
                        "array",
                        context.packageName
                    )
                    val options = context.resources.getStringArray(id)
                    val optionsObj = arrayListOf<Pair<String, Boolean>>()

                    optionsObj.add(Pair(options[0], true))
                    optionsObj.add(Pair(options[1], false))
                    optionsObj.add(Pair(options[2], false))
                    optionsObj.add(Pair(options[3], false))

                    val question = Question(
                        element,
                        optionsObj
                    )

                    questions.add(question)
                }
            }
            "cars" -> {
                carsQuestions.forEachIndexed { index, element ->
                    val stringResource = "cars_question_${index + 1}_options"
                    val id = context.resources.getIdentifier(
                        stringResource,
                        "array",
                        context.packageName
                    )
                    val options = context.resources.getStringArray(id)
                    val optionsObj = arrayListOf<Pair<String, Boolean>>()

                    optionsObj.add(Pair(options[0], true))
                    optionsObj.add(Pair(options[1], false))
                    optionsObj.add(Pair(options[2], false))
                    optionsObj.add(Pair(options[3], false))

                    val question = Question(
                        element,
                        optionsObj
                    )

                    questions.add(question)
                }
            }
            "dragon_ball" -> {
                dragonBallQuestions.forEachIndexed { index, element ->
                    val stringResource = "dragon_ball_question_${index + 1}_options"
                    val id = context.resources.getIdentifier(
                        stringResource,
                        "array",
                        context.packageName
                    )
                    val options = context.resources.getStringArray(id)
                    val optionsObj = arrayListOf<Pair<String, Boolean>>()

                    optionsObj.add(Pair(options[0], true))
                    optionsObj.add(Pair(options[1], false))
                    optionsObj.add(Pair(options[2], false))
                    optionsObj.add(Pair(options[3], false))

                    val question = Question(
                        element,
                        optionsObj
                    )

                    questions.add(question)
                }
            }
            "terminal_montage" -> {
                terminalMontageQuestions.forEachIndexed { index, element ->
                    val stringResource = "terminal_montage_question_${index + 1}_options"
                    val id = context.resources.getIdentifier(
                        stringResource,
                        "array",
                        context.packageName
                    )
                    val options = context.resources.getStringArray(id)
                    val optionsObj = arrayListOf<Pair<String, Boolean>>()

                    optionsObj.add(Pair(options[0], true))
                    optionsObj.add(Pair(options[1], false))
                    optionsObj.add(Pair(options[2], false))
                    optionsObj.add(Pair(options[3], false))

                    val question = Question(
                        element,
                        optionsObj
                    )

                    questions.add(question)
                }
            }
        }
    }
}