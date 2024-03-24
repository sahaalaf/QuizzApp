package com.example.quizzapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.quizzapp.databinding.ActivityRatingBinding

class RatingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRatingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRatingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val ratingBar: RatingBar = binding.ratingBar
        val submitButton: ImageButton = binding.submitButton
        val ratingScale: TextView = binding.ratingText
        val previousButton: ImageView = binding.previous

        ratingBar.setOnRatingBarChangeListener { rBar, fl, b ->
            ratingScale.text = fl.toString()
            when(rBar.rating.toInt()) {
                1 -> ratingScale.text = "Very Bad"
                2 -> ratingScale.text = "Bad"
                3 -> ratingScale.text = "Good"
                4 -> ratingScale.text = "Very Good"
                5 -> ratingScale.text = "Awesome"
                else -> ratingScale.text = " "
            }
        }

        submitButton.setOnClickListener{
            val message = ratingBar.rating.toString()
            Toast.makeText(this, "Rating is: $message", Toast.LENGTH_SHORT).show()
        }

        previousButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
