package com.example.numberguessinggame

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var randomNumber = 0
    private var maxNumber = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val difficultySpinner: Spinner = findViewById(R.id.difficulty_spinner)
        val guessInput: EditText = findViewById(R.id.guess_input)
        val checkButton: Button = findViewById(R.id.check_button)
        val resultText: TextView = findViewById(R.id.result_text)

        // Create an ArrayAdapter using a simple spinner layout and some difficulty levels
        ArrayAdapter.createFromResource(
            this,
            R.array.difficulty_levels,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            difficultySpinner.adapter = adapter
        }

        // Set an item selected listener for the Spinner
        difficultySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val difficulty = parent.getItemAtPosition(position).toString()
                maxNumber = when (difficulty) {
                    "Easy" -> 10
                    "Medium" -> 50
                    "Hard" -> 100
                    else -> 10
                }
                randomNumber = Random.nextInt(1, maxNumber + 1)
                resultText.text = "Guess a number between 1 and $maxNumber"
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }

        // Set a click listener for the Button
        checkButton.setOnClickListener {
            val guess = guessInput.text.toString().toIntOrNull()
            if (guess != null) {
                when {
                    guess < randomNumber -> resultText.text = "Too low! Try again."
                    guess > randomNumber -> resultText.text = "Too high! Try again."
                    else -> resultText.text = "Congratulations! You guessed the number."
                }
            } else {
                resultText.text = "Please enter a valid number."
            }
        }
    }
}
