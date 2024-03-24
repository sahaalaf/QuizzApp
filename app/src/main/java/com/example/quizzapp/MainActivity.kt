package com.example.quizzapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mcqsBtn: ImageButton = findViewById(R.id.mcqsBtn)
        val tfBtn: ImageButton = findViewById(R.id.tfBtn)
        val ratingBtn: ImageButton = findViewById(R.id.ratingBtn)

        mcqsBtn.setOnClickListener {
            val intent = Intent(this, MCQsActivity::class.java)
            startActivity(intent)
        }

        tfBtn.setOnClickListener{
            val intent = Intent(this, TrueFalseActivity::class.java)
            startActivity(intent)
        }

        ratingBtn.setOnClickListener{
            val intent = Intent(this, RatingActivity::class.java)
            startActivity(intent)
        }
    }
}