package com.cristian.calculafacil.calculation

import kotlin.math.pow

/**
 * Cálculos de saúde — lógica pura e testável.
 */
object Health {

    /** Índice de Massa Corporal: peso (kg) / altura² (m). */
    fun bmi(weightKg: Double, heightM: Double): Double =
        weightKg / heightM.pow(2)

    /** Classificação do IMC (padrão OMS). */
    enum class BmiCategory { UNDERWEIGHT, NORMAL, OVERWEIGHT, OBESITY }

    fun bmiCategory(bmi: Double): BmiCategory = when {
        bmi < 18.5 -> BmiCategory.UNDERWEIGHT
        bmi < 25.0 -> BmiCategory.NORMAL
        bmi < 30.0 -> BmiCategory.OVERWEIGHT
        else -> BmiCategory.OBESITY
    }

    /**
     * Metabolismo basal pela fórmula de Mifflin-St Jeor.
     * [sex] = 0 (feminino) ou 1 (masculino). Altura em cm, idade em anos.
     */
    fun basalMetabolism(weightKg: Double, heightCm: Double, ageYears: Double, sex: Double): Double {
        val base = 10.0 * weightKg + 6.25 * heightCm - 5.0 * ageYears
        return if (sex >= 1.0) base + 5.0 else base - 161.0
    }
}