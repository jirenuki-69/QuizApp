package com.example.quizapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.Clases.ScoreAdapter
import com.example.quizapp.db.AppDatabase
import com.example.quizapp.db.Entities.Profile
import com.example.quizapp.db.Entities.Score

private const val EXTRA_PROFILE_ID = "com.example.quizapp.EXTRA_PROFILE_ID"

class ProfileScoresActivity : AppCompatActivity() {

    companion object {
        fun startActivity(packageContext: AppCompatActivity, profileId: Int) {
            val intent = Intent(packageContext, ProfileScoresActivity::class.java)
            intent.putExtra(EXTRA_PROFILE_ID, profileId)
            packageContext.startActivity(intent)
        }
    }

    private lateinit var btnBack: Button
    private lateinit var autoComplete: AutoCompleteTextView
    private lateinit var tvProfileName: TextView
    private lateinit var rv: RecyclerView
    private lateinit var db: AppDatabase
    private lateinit var selectedProfile: Profile
    private lateinit var profileScores: MutableList<Score>
    private lateinit var adapter: ScoreAdapter
    private lateinit var orderAdapter: ArrayAdapter<String>
    private lateinit var orderCriteria: Array<String>

    override fun onResume() {
        super.onResume()
        orderCriteria = resources.getStringArray(R.array.profileOrderBy)
        orderAdapter = ArrayAdapter(this as Context, R.layout.dropdown_item, orderCriteria)
        autoComplete.setAdapter(orderAdapter)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_scores)

        db = AppDatabase.getInstance(this as Context)

        btnBack = findViewById(R.id.btnReturnScore)
        autoComplete = findViewById(R.id.autoCompleteTextViewProfileScores)
        tvProfileName = findViewById(R.id.tvProfileName)
        rv = findViewById(R.id.rvProfileScores)
        rv.layoutManager = LinearLayoutManager(this)

        if (intent != null) {
            val profileId = intent.getIntExtra(EXTRA_PROFILE_ID, 1)
            selectedProfile = db.ProfileDao().getProfile(profileId)
            tvProfileName.text = selectedProfile.name
        }

        loadScores()

        btnBack.setOnClickListener {
            finish()
        }

        autoComplete.setOnItemClickListener { parent, view, position, id ->
            if (position == 0) {
                adapter.clear()
                val newScores = db.ScoreDao().getProfileScores(selectedProfile.id)
                adapter.addAll(newScores)
            } else {
                adapter.clear()
                val newScores = db.ScoreDao().getProfileScoresOrderedByScore(selectedProfile.id)
                adapter.addAll(newScores)
            }
        }
    }

    private fun loadScores() {
        profileScores = db.ScoreDao().getProfileScores(selectedProfile.id)
        adapter = ScoreAdapter(profileScores)
        rv.adapter = adapter
    }
}