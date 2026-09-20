package com.cristian.calculafacil.model.engine

import com.cristian.calculafacil.model.CalcOutcome
import org.junit.Assert.assertTrue
import org.junit.Test

private fun CalcOutcome.values(): List<String> =
    (this as CalcOutcome.Success).rows.map { it.value }

private fun CalcOutcome.valueResList(): List<Int> =
    (this as CalcOutcome.Success).rows.mapNotNull { it.valueRes }

private fun CalcOutcome.isError(): Boolean = this is CalcOutcome.Error
private fun input(vararg pairs: Pair<String, Double>) = mapOf(*pairs)

class AverageEngineTest {
    @Test
    fun `average of four`() {
        val o = AverageEngine.calculate(input("g1" to 5.0, "g2" to 6.0, "g3" to 6.0, "g4" to 7.0))
        assertTrue(o.values().contains("6"))
    }

    @Test
    fun `grade above ten error`() {
        assertTrue(AverageEngine.calculate(input("g1" to 11.0, "g2" to 6.0, "g3" to 6.0, "g4" to 7.0)).isError())
    }

    @Test
    fun `missing grade error`() {
        assertTrue(AverageEngine.calculate(input("g1" to 5.0, "g2" to 6.0, "g3" to 6.0)).isError())
    }
}

class NeededGradeEngineTest {
    @Test
    fun `needed positive`() {
        val o = NeededGradeEngine.calculate(input("current" to 4.0, "passing" to 6.0))
        assertTrue(o.values().contains("2"))
    }

    @Test
    fun `already approved shows message`() {
        val o = NeededGradeEngine.calculate(input("current" to 8.0, "passing" to 6.0))
        assertTrue(o.valueResList().contains(com.cristian.calculafacil.R.string.calc_needed_already))
    }

    @Test
    fun `passing above ten error`() {
        assertTrue(NeededGradeEngine.calculate(input("current" to 5.0, "passing" to 11.0)).isError())
    }

    @Test
    fun `current negative error`() {
        assertTrue(NeededGradeEngine.calculate(input("current" to -1.0, "passing" to 6.0)).isError())
    }

    @Test
    fun `current zero is valid`() {
        val o = NeededGradeEngine.calculate(input("current" to 0.0, "passing" to 6.0))
        assertTrue(o.values().contains("6"))
    }

    @Test
    fun `current five valid`() {
        val o = NeededGradeEngine.calculate(input("current" to 5.0, "passing" to 6.0))
        assertTrue(o.values().contains("1"))
    }

    @Test
    fun `current ten valid and approved`() {
        val o = NeededGradeEngine.calculate(input("current" to 10.0, "passing" to 6.0))
        assertTrue(o.valueResList().contains(com.cristian.calculafacil.R.string.calc_needed_already))
    }

    @Test
    fun `current above ten error`() {
        assertTrue(NeededGradeEngine.calculate(input("current" to 10.1, "passing" to 6.0)).isError())
    }

    @Test
    fun `current fifteen error`() {
        assertTrue(NeededGradeEngine.calculate(input("current" to 15.0, "passing" to 6.0)).isError())
    }
}

class AgeEngineTest {
    @Test
    fun `valid date`() {
        val o = AgeEngine.calculate(input("day" to 1.0, "month" to 1.0, "year" to 2000.0))
        assertTrue(o.values().size == 1)
    }

    @Test
    fun `invalid month error`() {
        assertTrue(AgeEngine.calculate(input("day" to 1.0, "month" to 13.0, "year" to 2000.0)).isError())
    }

    @Test
    fun `invalid year error`() {
        assertTrue(AgeEngine.calculate(input("day" to 1.0, "month" to 5.0, "year" to 1800.0)).isError())
    }

    @Test
    fun `future date error`() {
        assertTrue(AgeEngine.calculate(input("day" to 1.0, "month" to 1.0, "year" to 2999.0)).isError())
    }

    @Test
    fun `invalid february 30 error`() {
        assertTrue(AgeEngine.calculate(input("day" to 30.0, "month" to 2.0, "year" to 2000.0)).isError())
    }

    @Test
    fun `invalid leap day on non leap year error`() {
        assertTrue(AgeEngine.calculate(input("day" to 29.0, "month" to 2.0, "year" to 2023.0)).isError())
    }

    @Test
    fun `valid leap day ok`() {
        val o = AgeEngine.calculate(input("day" to 29.0, "month" to 2.0, "year" to 2024.0))
        assertTrue(o.values().size == 1)
    }
}