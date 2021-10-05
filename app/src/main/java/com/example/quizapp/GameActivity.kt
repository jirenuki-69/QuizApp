package com.example.quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import com.example.quizapp.Clases.OptionAdapter

class GameActivity : AppCompatActivity() {
    private lateinit var listViewOptions: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        listViewOptions = findViewById(R.id.list_view)

        val options = arrayOf("Opción 1", "Opción 2", "Opción 3", "Opción 4")

        listViewOptions.adapter = OptionAdapter(this, options)

        listViewOptions.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(this, parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show()
        }
    }
}