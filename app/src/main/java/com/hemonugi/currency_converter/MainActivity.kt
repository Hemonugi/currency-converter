package com.hemonugi.currency_converter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hemonugi.currency_converter.databinding.ActivityMainBinding
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val ratesCalculator = RatesCalculator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

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

        buttons.forEach { button ->
            button.setOnClickListener {
                ratesCalculator.add(button.text as String)
                update()
            }
        }

        binding.numSwitch.setOnClickListener {
            ratesCalculator.switch()
            update()
            updateLabels()
        }

        binding.numBack.setOnClickListener {
            ratesCalculator.remove()
            update()
        }

        binding.numClear.setOnClickListener {
            ratesCalculator.clear()
            update()
        }
    }

    private fun update()
    {
        val formatter = DecimalFormat("#,###.##")
        binding.expInput.text = formatter.format(ratesCalculator.inputCurrency).toString()
        binding.resInput.text = formatter.format(ratesCalculator.outputCurrency).toString()
    }

    private fun updateLabels()
    {
        val expLabel = binding.expLabel.text
        binding.expLabel.text = binding.resLabel.text
        binding.resLabel.text = expLabel
    }
}