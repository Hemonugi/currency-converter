package com.hemonugi.currency_converter
import org.mariuszgromada.math.mxparser.Expression
import kotlin.math.floor

class RatesCalculator(private val rate: Rate) {

    private var input: String = ""
    private var operator: String = ""

    val inputCurrency: Float
        get() {
            if (input.isNotEmpty()) {
                return input.toFloat()
            }
            return 0f
        }

    val outputCurrency: Float
        get() = rate.output(inputCurrency)

    fun add(value: String) {
        if (value.substring(0, 1) == ".") {
            input = "0$value"
            return
        }

        input += value
    }

    fun addOperator(value: String) {
        if (input.isNotEmpty()) {
            operator = "$input$value"
            input = ""
            return
        }
    }

    fun switch() {
        rate.flip()
    }

    fun remove() {
        if (input.isNotEmpty()) {
            input = input.dropLast(1)
        }
    }

    fun clear() {
        operator = ""
        input = ""
    }

    fun calculate() {
        val expression = "$operator$input"
        val result = Expression(expression).calculate()

        operator = ""
        if (!result.isNaN()) {
            if (floor(result) == result) {
                input = result.toInt().toString()
                return
            }

            input = result.toString()
        }
    }
}