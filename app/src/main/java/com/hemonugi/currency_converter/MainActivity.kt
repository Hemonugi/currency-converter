package com.hemonugi.currency_converter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatDelegate
import com.hemonugi.currency_converter.databinding.ActivityMainBinding
import java.text.DecimalFormat

data class ArithmeticButton (val button: Button, val operator: String)

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val ratesCalculator = RatesCalculator(Rate(100, 20.8228f))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

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

        val arithmeticButtons = listOf(
            ArithmeticButton(binding.sum, "+"),
            ArithmeticButton(binding.division, "/"),
            ArithmeticButton(binding.multiply, "*"),
            ArithmeticButton(binding.subtract, "-"),
        )

        arithmeticButtons.forEach { arithmeticButton ->
            arithmeticButton.button.setOnClickListener {
                ratesCalculator.addOperator( arithmeticButton.operator)
            }
        }

        binding.calculate.setOnClickListener {
            ratesCalculator.calculate()
            update()
        }


        val parser = CrbXmlParser()
        val currencies = parser.parse(assets.open("XML_daily.xml"))
        Log.d("test", currencies.find { it.charCode == "AMD" }?.value ?: currencies.first().value)
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