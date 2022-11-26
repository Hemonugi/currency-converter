package com.hemonugi.currency_converter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hemonugi.currency_converter.databinding.ActivityMainBinding
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val currentCourse = 6.30
    private var expressionString = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setListeners(binding)
    }

    private fun setListeners(binding: ActivityMainBinding) {
        val buttons = listOf(
            binding.numDot,
            binding.numZero,
            binding.numOne,
            binding.numTwo,
            binding.numThree,
            binding.numFour,
            binding.numFive,
            binding.numSix,
            binding.numSeven,
            binding.numEight,
            binding.numNine,
        )

        buttons.forEach { button -> button.setOnClickListener {
            var textInButton = button.text
            if (textInButton.substring(0, 1) == binding.numDot.text) {
                textInButton = "0$textInButton";
            }

            expressionString += textInButton
                binding.expInput.text = expressionString
                calculateRate(binding)
            }
        }

        binding.numBack.setOnClickListener {
            expressionString = ""
            binding.expInput.text = expressionString
            binding.resInput.text = ""
        }

        binding.numClear.setOnClickListener {
            if (expressionString.isNotEmpty()) {
                expressionString = expressionString.dropLast(1)
                binding.expInput.text = expressionString
                calculateRate(binding)
            }
        }
    }

    private fun calculateRate(binding: ActivityMainBinding) {
        if (expressionString.isNotEmpty()) {
            val df = DecimalFormat("#.##")
            binding.resInput.text = df.format(expressionString.toFloat() / currentCourse);
        } else {
            binding.resInput.text = ""
        }
    }
}