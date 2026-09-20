package com.cristian.calculafacil.calculation

/**
 * Conversão de moedas — lógica pura e testável.
 *
 * A taxa de cada moeda é expressa como "1 unidade da moeda = [rate] BRL".
 * A conversão entre duas moedas usa:
 *   resultado = valor * taxaDe / taxaPara
 * (converte o valor para BRL multiplicando pela taxa da origem e, então,
 *  divide pela taxa do destino para chegar à moeda de destino).
 * A [CurrencyRateSource] isola a origem dos dados, permitindo no futuro
 * substituir a fonte estática por taxas atualizadas (rede) sem tocar na UI.
 * Se a fonte não fornecer a taxa de uma moeda ([rate] retorna `null`), a
 * conversão retorna `null` e o app nunca exibe um valor falso.
 */
object CurrencyConverter {

    /** Fonte de taxas. Implementação estática por enquanto; pronta para rede. */
    interface CurrencyRateSource {
        /** Taxa de cada moeda relativa a BRL (1 moeda = rate BRL). */
        fun rate(currencyCode: String): Double?
    }

    /** Moedas suportadas pela implementação atual. */
    val supportedCodes: List<String> = listOf("BRL", "USD", "EUR", "ARS", "PYG", "UYU")

    fun convert(value: Double, fromCode: String, toCode: String, source: CurrencyRateSource): Double? {
        if (fromCode == toCode) return value
        val fromRate = source.rate(fromCode) ?: return null
        val toRate = source.rate(toCode) ?: return null
        if (fromRate == 0.0 || toRate == 0.0) return null
        // valor * taxaDe converte para BRL; dividir por taxaPara dá a moeda de destino
        return value * fromRate / toRate
    }

    fun formatCode(value: Double, code: String): String =
        "${Format.fixed(value, 2)} $code"
}

/** Taxas fixas de exemplo, relativas a BRL. Arquitetura preparada para rede. */
object StaticCurrencyRates : CurrencyConverter.CurrencyRateSource {
    override fun rate(currencyCode: String): Double? = when (currencyCode) {
        "BRL" -> 1.0
        "USD" -> 5.20
        "EUR" -> 5.60
        "ARS" -> 0.0055
        "PYG" -> 0.00068
        "UYU" -> 0.13
        else -> null
    }
}