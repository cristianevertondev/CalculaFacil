package com.cristian.calculafacil.calculation

/**
 * Operações de porcentagem — lógica pura e testável.
 */
object Percentage {

    /** Quanto é [percent]% de [value]. */
    fun percentOf(value: Double, percent: Double): Double =
        value * percent / 100.0

    /** Valor final após aplicar [discountPercent]% de desconto sobre [price]. */
    fun applyDiscount(price: Double, discountPercent: Double): Double =
        price * (100.0 - discountPercent) / 100.0

    /** Valor em reais do desconto ([discountPercent]% sobre [price]). */
    fun discountAmount(price: Double, discountPercent: Double): Double =
        price * discountPercent / 100.0

    /** Valor final após aplicar [increasePercent]% de aumento sobre [value]. */
    fun applyIncrease(value: Double, increasePercent: Double): Double =
        value * (100.0 + increasePercent) / 100.0

    /** Valor em reais do aumento ([increasePercent]% sobre [value]). */
    fun increaseAmount(value: Double, increasePercent: Double): Double =
        value * increasePercent / 100.0
}