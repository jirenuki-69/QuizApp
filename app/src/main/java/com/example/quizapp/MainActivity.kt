package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.SettingsActivity

class MainActivity : AppCompatActivity() {
    private lateinit var playButton: Button
    private lateinit var optionsButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val actionsBar = supportActionBar
        actionsBar!!.title = resources.getString(R.string.home_text)

        playButton = findViewById(R.id.button_play)
        optionsButton = findViewById(R.id.button_options)

        playButton.setOnClickListener { _ -> }

        optionsButton.setOnClickListener { _ ->
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        when (requestCode){
//            5 -> when (resultCode){
//                RESULT_OK ->Toast.makeText(
//                    this,
//                    "OK ${data!!.getStringExtra("CHEAT_TEST_TEXT")}",
//                    Toast.LENGTH_LONG).show()
//            }
//            RESULT_CANCELED -> Toast.makeText(this, "CANCEL",Toast.LENGTH_LONG).show()
//
//        }
//    }
}