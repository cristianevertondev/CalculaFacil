package com.cristian.calculafacil.calculation

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate

class EducationTest {

    @Test
    fun `average of grades`() {
        assertEquals(7.5, Education.average(listOf(7.0, 8.0)), 1e-9)
    }

    @Test
    fun `average of four grades`() {
        assertEquals(6.0, Education.average(listOf(5.0, 6.0, 6.0, 7.0)), 1e-9)
    }

    @Test
    fun `average empty`() {
        assertEquals(0.0, Education.average(emptyList()), 1e-9)
    }

    @Test
    fun `needed grade below passing`() {
        assertEquals(2.0, Education.neededGrade(4.0, 6.0), 1e-9)
    }

    @Test
    fun `needed grade already passing`() {
        assertEquals(0.0, Education.neededGrade(8.0, 6.0), 1e-9)
    }

    @Test
    fun `needed grade equal passing`() {
        assertEquals(0.0, Education.neededGrade(6.0, 6.0), 1e-9)
    }
}

class AgeCalculatorTest {

    @Test
    fun `age before birthday`() {
        val today = LocalDate.of(2026, 9, 2)
        assertEquals(29, AgeCalculator.age(15, 10, 1996, today))
    }

    @Test
    fun `age after birthday`() {
        val today = LocalDate.of(2026, 9, 2)
        assertEquals(30, AgeCalculator.age(15, 1, 1996, today))
    }

    @Test
    fun `age on birthday`() {
        val today = LocalDate.of(2026, 9, 2)
        assertEquals(30, AgeCalculator.age(2, 9, 1996, today))
    }

    @Test
    fun `age newborn`() {
        val today = LocalDate.of(2026, 9, 2)
        assertEquals(0, AgeCalculator.age(1, 9, 2026, today))
    }
}

class BirthDateTest {

    @Test
    fun `valid leap day`() {
        assertEquals(LocalDate.of(2024, 2, 29), AgeCalculator.birthDate(29, 2, 2024))
    }

    @Test
    fun `invalid leap day on non leap year`() {
        assertEquals(null, AgeCalculator.birthDate(29, 2, 2023))
    }

    @Test
    fun `invalid february 30`() {
        assertEquals(null, AgeCalculator.birthDate(30, 2, 2000))
    }

    @Test
    fun `invalid april 31`() {
        assertEquals(null, AgeCalculator.birthDate(31, 4, 2000))
    }

    @Test
    fun `invalid month`() {
        assertEquals(null, AgeCalculator.birthDate(1, 13, 2000))
    }

    @Test
    fun `valid regular date`() {
        assertEquals(LocalDate.of(1996, 10, 15), AgeCalculator.birthDate(15, 10, 1996))
    }
}