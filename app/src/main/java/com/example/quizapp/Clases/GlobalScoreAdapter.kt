package com.example.quizapp.Clases

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.R

class GlobalScoreAdapter(val scores: MutableList<GlobalScore>, val onProfileSelected: (Int) -> Unit) :
    RecyclerView.Adapter<GlobalScoreAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var tvTotalScore: TextView
        private var tvGamesPlayed: TextView
        private var tvGamesUsingHints: TextView
        private var btnProfileName: Button

        init {
            tvTotalScore = view.findViewById(R.id.score_general_total_score_text)
            tvGamesPlayed = view.findViewById(R.id.games_played_score)
            tvGamesUsingHints = view.findViewById(R.id.score_games_won_hints_text)
            btnProfileName = view.findViewById(R.id.btn_personal_score)
        }

        fun bind(score: GlobalScore, position: Int) {
            btnProfileName.text = score.profileName
            tvTotalScore.text = score.score.toString()
            tvGamesPlayed.text = score.gamesPlayed.toString()
            tvGamesUsingHints.text = score.gamesUsingHint.toString()

            btnProfileName.setOnClickListener { onProfileSelected(position) }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clear() {
        scores.clear()
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addAll(list: MutableList<GlobalScore>) {
        list.forEach {
            scores.add(it)
        }

        notifyDataSetChanged()
    }

    fun getItem(position: Int): GlobalScore = scores[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.score_item_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(scores[position], position)
    }

    override fun getItemCount(): Int = scores.size
}