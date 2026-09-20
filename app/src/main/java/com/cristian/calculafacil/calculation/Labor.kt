package com.cristian.calculafacil.calculation

/**
 * Cálculos trabalhistas — lógica pura e testável.
 * Convenções usadas (referências legais brasileiras comuns):
 *  - Mês comercial = 30 dias.
 *  - Jornada mensal de referência = 220 horas (44h/semana).
 */
object Labor {

    /** Salário por hora a partir do salário mensal (base 220h). */
    fun hourlyFromMonthly(monthly: Double): Double = monthly / 220.0

    /** Salário por dia a partir do salário mensal (base 30 dias). */
    fun dailyFromMonthly(monthly: Double): Double = monthly / 30.0

    /**
     * Valor total de horas extras.
     * [hourlyRate] é o valor da hora normal e [percent] o adicional (ex.: 50 ou 100).
     */
    fun overtimeValue(hourlyRate: Double, percent: Double, hours: Double): Double =
        hourlyRate * (1.0 + percent / 100.0) * hours

    /** Valor base das férias (salário por dia × dias). */
    fun vacationBase(monthly: Double, days: Double): Double = monthly / 30.0 * days

    /** Terço constitucional sobre um valor de férias. */
    fun vacationThird(base: Double): Double = base / 3.0

    /** Total de férias (base + terço). */
    fun vacationTotal(monthly: Double, days: Double): Double {
        val base = vacationBase(monthly, days)
        return base + vacationThird(base)
    }

    /** Valor do 13º salário proporcional aos [months] trabalhados no ano. */
    fun thirteenth(monthly: Double, months: Double): Double = monthly / 12.0 * months

    /** Saldo de salário proporcional aos dias trabalhados no mês. */
    fun salaryBalance(monthly: Double, daysWorked: Double): Double = monthly / 30.0 * daysWorked

    /** Férias proporcionais pelos meses trabalhados (avos). */
    fun proportionalVacation(monthly: Double, monthsWorked: Double): Double =
        monthly / 12.0 * monthsWorked
}