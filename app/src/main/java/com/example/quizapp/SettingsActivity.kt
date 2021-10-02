package com.example.quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val actionsBar = supportActionBar
        actionsBar!!.title = resources.getString(R.string.options_text)

        actionsBar.setDisplayHomeAsUpEnabled(true)
    }
}