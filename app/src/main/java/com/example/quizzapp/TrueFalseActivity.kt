package com.example.quizzapp

import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.quizzapp.databinding.ActivityMcqsBinding

class TrueFalseActivity : AppCompatActivity() {

    private val questions = arrayOf(
        "Mobile computing always provides high battery life.",
        "GPS stands for General Positioning System.",
        "iOS is the mobile operating system developed by Google.",
        "Bluetooth is commonly used for long-range communication between mobile devices.",
        "Steam is a mobile application distribution platform.",
        "RFID technology allows mobile devices to interact with real-world objects using embedded chips.",
        "Desktop is a mobile device form factor.",
        "Cloud computing involves offloading tasks to remote servers.",
        "NFC stands for National Federation of Communication.",
        "5G introduced significant improvements in data transfer speeds."
    )

    private val correctAnswers = arrayOf(
        0, // Answer for the first question (Not a characteristic of mobile computing)
        1,  // Answer for the second question (GPS stands for General Positioning System)
        0, // Answer for the third question (iOS is not developed by Google)
        0, // Answer for the fourth question (Bluetooth is not commonly used for long-range communication)
        0, // Answer for the fifth question (Steam is not a mobile application distribution platform)
        1,  // Answer for the sixth question (RFID technology allows interaction with real-world objects)
        0, // Answer for the seventh question (Desktop is not a mobile device form factor)
        1,  // Answer for the eighth question (Cloud computing involves offloading tasks to remote servers)
        0, // Answer for the ninth question (NFC does not stand for National Federation of Communication)
        1   // Answer for the tenth question (5G introduced significant improvements in data transfer speeds)
    )

    private lateinit var progressBar: ProgressBar
    private var currentQuestionIndex = 0
    private var score = 0
    private lateinit var binding: ActivityMcqsBinding
    private var isNextButtonEnabled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMcqsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressBar = binding.progressBar
        progressBar.max = questions.size

        showQuestion()

        // show next question
        binding.nextButton.setOnClickListener {
            if(isNextButtonEnabled)
            {
                showNextQuestion()
            }
            else
            {
                Toast.makeText(this, "Please select an option for the current question", Toast.LENGTH_SHORT).show()
            }
        }

        // show previous question
        binding.previousButton.setOnClickListener {
            if (currentQuestionIndex > 0) {
                currentQuestionIndex--
                showQuestion()
                decreaseProgressBar()
                binding.nextButton.isEnabled = false
                binding.answerRadioGroup.clearCheck()
            } else {
                Toast.makeText(this, "You are already at the first question", Toast.LENGTH_SHORT).show()
            }
        }

        // the image view which takes us to the main activity
        val previous = binding.previous
        previous.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showQuestion() {
        val questionTextView = binding.questionTextView
        val answerRadioGroup = binding.answerRadioGroup
        val indexText = binding.indexText

        questionTextView.text = questions[currentQuestionIndex]

        val currentIndex = currentQuestionIndex + 1
        val totalQuestions = questions.size
        indexText.text = "$currentIndex/$totalQuestions"
        answerRadioGroup.removeAllViews()

        // Adding true/false radio buttons with style
        val trueButton = RadioButton(this)
        trueButton.text = "True"
        trueButton.setTextColor(resources.getColor(R.color.white)) // Deprecated, but still works
        trueButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19f)
        trueButton.setTypeface(resources.getFont(R.font.merriweathersans_medium))
        answerRadioGroup.addView(trueButton)

        val falseButton = RadioButton(this)
        falseButton.text = "False"
        falseButton.setTextColor(resources.getColor(R.color.white)) // Deprecated, but still works
        falseButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
        falseButton.setTypeface(resources.getFont(R.font.merriweathersans_medium))
        answerRadioGroup.addView(falseButton)

        // Add a listener to the radio group to enable the Next button when an option is selected
        answerRadioGroup.setOnCheckedChangeListener { _, _ ->
            isNextButtonEnabled = true
            binding.nextButton.isEnabled = true
        }
    }

    private fun showNextQuestion() {
        val answerRadioGroup = binding.answerRadioGroup
        val checkedRadioButtonId = answerRadioGroup.checkedRadioButtonId

        if (checkedRadioButtonId == -1) {
            Toast.makeText(this, "Please select an option for the current question", Toast.LENGTH_SHORT).show()
            return
        }

        val selectedOption = answerRadioGroup.indexOfChild(answerRadioGroup.findViewById(checkedRadioButtonId))
        if (selectedOption == correctAnswers[currentQuestionIndex]) {
            score++
        }

        currentQuestionIndex++

        if (currentQuestionIndex < questions.size) {
            showQuestion()
            increaseProgressBar()
            binding.nextButton.isEnabled = false // Disable Next button after showing next question
        } else {
            Toast.makeText(this, "Quiz completed. Score: $score out of ${questions.size}", Toast.LENGTH_LONG).show()
        }
    }

    private fun showPreviousQuestion() {
        if (currentQuestionIndex > 0) {
            currentQuestionIndex--
            showQuestion()
            decreaseProgressBar()
        } else {
            Toast.makeText(this, "You are already at the first question", Toast.LENGTH_SHORT).show()
        }
    }

    private fun increaseProgressBar() {
        progressBar.progress = currentQuestionIndex + 1
    }

    private fun decreaseProgressBar() {
        progressBar.progress = currentQuestionIndex
    }
}
