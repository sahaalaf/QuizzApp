package com.example.quizzapp
import android.content.Intent
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.quizzapp.databinding.ActivityMcqsBinding

class MCQsActivity : AppCompatActivity() {

    private val questions = arrayOf(
        "Which of the following is NOT a characteristic of mobile computing?",
        "What does GPS stand for in the context of mobile computing?",
        "Which mobile operating system is developed by Apple Inc.?",
        "Which wireless technology is commonly used for short-range communication between mobile devices?",
        "Which of the following is NOT a mobile application distribution platform?",
        "Which technology allows mobile devices to interact with real-world objects using embedded chips?",
        "Which of the following is NOT a mobile device form factor?",
        "Which mobile computing concept involves offloading tasks to remote servers?",
        "What does NFC stand for in the context of mobile computing?",
        "Which mobile network generation introduced significant improvements in data transfer speeds?"
    )

    private val options = arrayOf(
        arrayOf("Mobility", "Low battery life", "Portability", "Wireless connectivity"),
        arrayOf("Global Personal System", "General Positioning System", "Geographic Positioning System", "Global Positioning System"),
        arrayOf("Android", "iOS", "Windows Mobile", "BlackBerry OS"),
        arrayOf("WiMAX", "Bluetooth", "NFC", "LTE"),
        arrayOf("Google Play Store", "Apple App Store", "Steam", "Amazon Appstore"),
        arrayOf("RFID", "GPS", "Bluetooth", "Wi-Fi"),
        arrayOf("Smartphone", "Tablet", "Desktop", "Wearable"),
        arrayOf("Cloud computing", "Edge computing", "Fog computing", "Distributed computing"),
        arrayOf("Near-Field Communication", "Next-Generation Connectivity", "Network File Control", "National Federation of Communication"),
        arrayOf("2G", "3G", "4G", "5G")
    )

    private val correctAnswers = arrayOf(
        1, // Index of correct answer for the first question
        3, // Index of correct answer for the second question
        1, // Index of correct answer for the third question
        1, // Index of correct answer for the fourth question
        2, // Index of correct answer for the fifth question
        0, // Index of correct answer for the sixth question
        2, // Index of correct answer for the seventh question
        0, // Index of correct answer for the eighth question
        0, // Index of correct answer for the ninth question
        3  // Index of correct answer for the tenth question
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

        binding.nextButton.setOnClickListener {
            if (isNextButtonEnabled) {
                showNextQuestion()
            } else {
                Toast.makeText(this, "Please select an option for the current question", Toast.LENGTH_SHORT).show()
            }
        }
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



        binding.answerRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            // Check if any option is selected for the current question
            isNextButtonEnabled = checkedId != -1
            // Enable or disable the "Next" button based on the selection
            binding.nextButton.isEnabled = isNextButtonEnabled
        }
        val previous = binding.previous
        previous.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun decreaseProgressBar() {
        progressBar.progress = currentQuestionIndex
    }


    private fun showQuestion() {
        val questionTextView = binding.questionTextView
        val answerRadioGroup = binding.answerRadioGroup
        val indexText = binding.indexText  // Move this inside the method

        questionTextView.text = questions[currentQuestionIndex]

        // Calculate the current index and total questions
        val currentIndex = currentQuestionIndex + 1
        val totalQuestions = questions.size

        // Update the indexText to display the current index and total questions
        indexText.text = "$currentIndex/$totalQuestions"

        clearRadioButtons(answerRadioGroup)

        for (i in options[currentQuestionIndex].indices) {
            val radioButton = RadioButton(this)
            radioButton.text = options[currentQuestionIndex][i]
            radioButton.setTextColor(getColor(R.color.white))
            radioButton.textSize = 18f
            radioButton.typeface = resources.getFont(R.font.merriweathersans_medium)
            answerRadioGroup.addView(radioButton)
        }

        // Check if any option is selected for the current question
        isNextButtonEnabled = answerRadioGroup.checkedRadioButtonId != -1
        // Enable or disable the "Next" button based on the selection
        binding.nextButton.isEnabled = isNextButtonEnabled
    }

    private fun clearRadioButtons(answerRadioGroup: RadioGroup) {
        answerRadioGroup.removeAllViews()
    }

    private fun showNextQuestion() {
        val answerRadioGroup = binding.answerRadioGroup
        val checkedRadioButtonId = answerRadioGroup.checkedRadioButtonId

        val selectedOption = answerRadioGroup.indexOfChild(answerRadioGroup.findViewById(checkedRadioButtonId))
        if (selectedOption == correctAnswers[currentQuestionIndex]) {
            score++
        }

        currentQuestionIndex++

        if (currentQuestionIndex < questions.size) {
            showQuestion()
            increaseProgressBar()
            binding.nextButton.isEnabled = false
        } else {
            Toast.makeText(this, "Quiz completed. Score: $score out of ${questions.size}", Toast.LENGTH_LONG).show()
        }
    }

    private fun increaseProgressBar() {
        progressBar.progress = currentQuestionIndex + 1
    }
}