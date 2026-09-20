package com.cristian.calculafacil.calculation

import java.time.DateTimeException
import java.time.LocalDate

/**
 * Cálculos educacionais e de idade — lógica pura e testável.
 */
object Education {

    /** Média aritmética das notas. */
    fun average(grades: List<Double>): Double =
        if (grades.isEmpty()) 0.0 else grades.sum() / grades.size

    /** Nota necessária para atingir [passing] com média atual [current]. */
    fun neededGrade(current: Double, passing: Double): Double =
        (passing - current).coerceAtLeast(0.0)
}

/** Cálculo de idade — lógica pura e testável. */
object AgeCalculator {

    /**
     * Converte [day]/[month]/[year] em [LocalDate], ou retorna `null` se a
     * data não existir no calendário (ex.: 30/02, 31/04, 29/02 em ano não bissexto).
     */
    fun birthDate(day: Int, month: Int, year: Int): LocalDate? = try {
        LocalDate.of(year, month, day)
    } catch (_: DateTimeException) {
        null
    }

    /** Idade em anos completos até [today]. */
    fun age(birthDay: Int, birthMonth: Int, birthYear: Int, today: LocalDate): Int {
        var years = today.year - birthYear
        val hadBirthday = today.monthValue > birthMonth ||
            (today.monthValue == birthMonth && today.dayOfMonth >= birthDay)
        if (!hadBirthday) years--
        return years
    }
}