package com.hemonugi.currency_converter

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource


class RatesCalculatorTest
{
    @ParameterizedTest
    @CsvSource(
        "10, /, 5, 2",
        "10, *, 5, 50",
        "10, +, 5, 15",
        "10, -, 5, 5",
        "10, ÷, 5, 5",
    )
    fun testCalculate(value1: String, operator: String, value2: String, expectedResult: Float) {
        val calc = RatesCalculator()

        calc.add(value1)
        calc.addOperator(operator)
        calc.add(value2)
        calc.calculate()

        assertEquals(expectedResult, calc.inputCurrency)
    }

}