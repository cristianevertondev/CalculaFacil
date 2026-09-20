package com.cristian.calculafacil.calculation

/**
 * Juros — lógica pura e testável.
 */
object Interest {

    /** Juros simples: capital * taxa * tempo. */
    fun simpleInterest(principal: Double, ratePercent: Double, months: Double): Double =
        principal * (ratePercent / 100.0) * months

    /** Montante com juros simples. */
    fun simpleAmount(principal: Double, ratePercent: Double, months: Double): Double =
        principal + simpleInterest(principal, ratePercent, months)

    /** Montante com juros compostos (taxa mensal sobre meses). */
    fun compoundAmount(principal: Double, ratePercent: Double, months: Double): Double =
        principal * Math.pow(1.0 + ratePercent / 100.0, months)

    /** Juros acumulados em composição. */
    fun compoundInterest(principal: Double, ratePercent: Double, months: Double): Double =
        compoundAmount(principal, ratePercent, months) - principal

    /**
     * Prestação Price (parcelas fixas).
     *   PMT = PV * i / (1 - (1 + i)^-n)
     */
    fun priceInstallment(principal: Double, ratePercent: Double, months: Double): Double {
        val i = ratePercent / 100.0
        if (i == 0.0) return principal / months
        val factor = Math.pow(1.0 + i, months)
        return principal * i * factor / (factor - 1.0)
    }

    /**
     * Primeira prestação SAC: amortização fixa + juros sobre o saldo inicial.
     */
    fun sacFirstInstallment(principal: Double, ratePercent: Double, months: Double): Double {
        val amortization = principal / months
        val firstInterest = principal * (ratePercent / 100.0)
        return amortization + firstInterest
    }

    /** Última prestação SAC (juros sobre o saldo residual mínimo). */
    fun sacLastInstallment(principal: Double, ratePercent: Double, months: Double): Double {
        val amortization = principal / months
        val lastInterest = amortization * (ratePercent / 100.0)
        return amortization + lastInterest
    }

    /** Total pago em Price. */
    fun priceTotal(principal: Double, ratePercent: Double, months: Double): Double =
        priceInstallment(principal, ratePercent, months) * months

    /** Total pago em SAC: soma de uma progressão aritmética de juros + principal. */
    fun sacTotal(principal: Double, ratePercent: Double, months: Double): Double {
        val amortization = principal / months
        val firstInterest = principal * (ratePercent / 100.0)
        val lastInterest = amortization * (ratePercent / 100.0)
        val sumInterest = (firstInterest + lastInterest) * months / 2.0
        return principal + sumInterest
    }
}