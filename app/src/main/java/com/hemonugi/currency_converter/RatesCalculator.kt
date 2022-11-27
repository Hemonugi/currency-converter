package com.hemonugi.currency_converter

class RatesCalculator {

    private var input: String = ""

    val inputCurrency: Float
        get() {
            if (input.isNotEmpty()) {
                return input.toFloat()
            }
            return 0f
        }

    val outputCurrency: Float
        get() = inputCurrency / 6.30f

    fun add(value: String) {
        if (value.substring(0, 1) == ".") {
            input = "0$value"
            return
        }

        input += value
    }

    fun remove() {
        if (input.isNotEmpty()) {
            input.dropLast(1)
        }
    }

    fun clear() {
        input = ""
    }
}