package com.cristian.calculafacil.calculation

/**
 * Regra de três simples: A está para B assim como C está para X.
 */
object Proportion {

    /** Resolve X dado `a/b = c/x`. */
    fun solveForX(a: Double, b: Double, c: Double): Double =
        b * c / a

    /**
     * Resolve a proporção [a] : [b] = [c] : x.
     * [a] não pode ser zero (divisão por zero).
     */
    fun ruleOfThree(a: Double, b: Double, c: Double): Double = solveForX(a, b, c)
}