package com.example.quizapp.Clases

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.R
import com.example.quizapp.db.Entities.Score

class ScoreAdapter(val scores: MutableList<Score>) : RecyclerView.Adapter<ScoreAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var totalScoreTextView: TextView
        private var hintsEnabledTextView: TextView
        private var DifficutyTextView: TextView
        private var NumberOfQuestionsTextView: TextView
        private var DateTextView: TextView

        init {
            totalScoreTextView = view.findViewById(R.id.score_total_score_text)
            hintsEnabledTextView = view.findViewById(R.id.score_hints_enabled_text)
            DifficutyTextView = view.findViewById(R.id.score_difficulty_text)
            NumberOfQuestionsTextView = view.findViewById(R.id.score_number_of_questions_text)
            DateTextView = view.findViewById(R.id.score_date_text)
        }

        fun bind(score: Score) {
            totalScoreTextView.text = score.totalScore.toString()
            hintsEnabledTextView.text = score.hintsEnabled.toString()
            DifficutyTextView.text = score.difficulty
            NumberOfQuestionsTextView.text = score.numberOfQuestions.toString()
            DateTextView.text = score.date
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clear() {
        scores.clear()
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addAll(list: MutableList<Score>) {
        list.forEach {
            scores.add(it)
        }

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.plain_score_item_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(scores[position])
    }

    override fun getItemCount(): Int = scores.size
}