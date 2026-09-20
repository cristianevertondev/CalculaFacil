package com.cristian.calculafacil.calculation

import kotlin.math.abs

/**
 * Funções puras de conversão de texto em número, independentes de Android.
 * Aceitam vírgula ou ponto como separador decimal (padrão BR e internacional)
 * e ignoram espaços e símbolos de moeda.
 */
object Parsing {

    /**
     * Converte uma entrada de texto em [Double], ou retorna `null` se inválida.
     * Exemplos válidos: "123", "1.234,56", "1234.56", "12,5 %", "R$ 9,99", "-3".
     */
    fun parseDecimal(input: String): Double? {
        if (input.isBlank()) return null
        val cleaned = input.trim()
            .replace("R$", "")
            .replace("%", "")
            .replace(" ", "")
            .replace("…", "")
        if (cleaned.isEmpty()) return null

        var normalized = cleaned
        val commaCount = normalized.count { it == ',' }
        val dotCount = normalized.count { it == '.' }

        val candidate: String = when {
            commaCount == 0 && dotCount == 0 -> normalized
            commaCount == 0 && dotCount == 1 -> normalized
            // Apenas vírgula decimal: "12,5"
            commaCount == 1 && dotCount == 0 -> normalized.replace(",", ".")
            // Ambos os separadores presentes: o ÚLTIMO é o decimal
            //   "1.234,56" (BR) → 1234.56 ; "1,234.56" (US) → 1234.56
            commaCount >= 1 && dotCount >= 1 -> {
                val lastIsComma = normalized.lastIndexOf(',') > normalized.lastIndexOf('.')
                if (lastIsComma) {
                    normalized.replace(".", "").replace(",", ".")
                } else {
                    normalized.replace(",", "")
                }
            }
            // Apenas pontos, usados como milhar: "1.234.567"
            commaCount == 0 && dotCount > 1 -> normalized.replace(".", "")
            // múltiplas vírgulas (ex.: "1,2,3") → inválido
            commaCount > 1 && dotCount == 0 -> return null
            else -> return null
        }

        val result = candidate.toDoubleOrNull() ?: return null
        return result
    }

    /**
     * Divide um valor em partes e testa se é um número inteiro (não fracionário).
     * Útil para validar campos do tipo [com.cristian.calculafacil.model.FieldType.Integer].
     */
    fun isWhole(value: Double): Boolean = value == abs(value).toLong().toDouble() && value.isFinite()
}