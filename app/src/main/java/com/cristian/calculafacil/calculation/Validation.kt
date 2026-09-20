package com.cristian.calculafacil.calculation

/**
 * Validações numéricas compartilhadas pelas engines (lógica pura, testável).
 */
object Validation {

    /** O valor é finito e >= 0. */
    fun requirePositive(value: Double): Boolean =
        value.isFinite() && value >= 0.0

    /** O valor é finito e > 0 (ex.: divisor, quantidade). */
    fun requireStrictlyPositive(value: Double): Boolean =
        value.isFinite() && value > 0.0

    /** O valor é finito (aceita negativos quando fizer sentido). */
    fun requireFinite(value: Double): Boolean = value.isFinite()
}