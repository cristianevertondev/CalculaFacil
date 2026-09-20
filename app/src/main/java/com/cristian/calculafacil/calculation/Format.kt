package com.cristian.calculafacil.calculation

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

/**
 * Formatação de valores independente de Android (Java puro, testável em JVM).
 * Usa locale pt-BR para moeda, como contexto do produto.
 */
object Format {

    /** Formata como moeda BRL, ex.: R$ 1.234,56. */
    fun money(value: Double): String =
        NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
            .format(value)
            .replace('\u00A0', ' ')

    /** Formata um número com até [decimals] casas decimais, com separador de milhar. */
    fun number(value: Double, decimals: Int = 2): String {
        if (value == 0.0) return "0"
        val df = DecimalFormat("#,##0")
        df.minimumFractionDigits = 0
        df.maximumFractionDigits = decimals
        return df.format(value)
    }

    /** Formata um inteiro sem casas decimais. */
    fun integer(value: Double): String = number(value, 0)

    /** Formata com exatamente [decimals] casas decimais (preenche zeros). */
    fun fixed(value: Double, decimals: Int = 2): String {
        val df = DecimalFormat("0." + "0".repeat(decimals))
        df.isGroupingUsed = false
        return df.format(value)
    }
}