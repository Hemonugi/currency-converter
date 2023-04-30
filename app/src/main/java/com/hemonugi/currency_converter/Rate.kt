package com.hemonugi.currency_converter

/**
 * Описывает отношение курса одной валюты к другой
 *
 * @param rate сколько нужнно вылюты output, чтобы купить одну единицу input
 */
class Rate(private val nominal: Int, private val rate: Float) {

    private var flipped = false

    fun output(input: Float): Float {
        return if (flipped) input * (nominal / rate) else input / (nominal / rate)
    }

    /**
     * Меняет местами валюту
     */
    fun flip() {
        flipped = !flipped;
    }
}