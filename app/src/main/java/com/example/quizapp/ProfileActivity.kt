package com.example.quizapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.quizapp.db.AppDatabase

class ProfileActivity : AppCompatActivity(){
    private lateinit var backButton: Button
    private lateinit var db: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        db = AppDatabase.getInstance(this as Context)
        backButton = findViewById(R.id.profile_backHome_button)

        backButton.setOnClickListener {
            finish()
        }
    }
}