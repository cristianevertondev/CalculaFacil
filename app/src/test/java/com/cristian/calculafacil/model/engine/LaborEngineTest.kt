package com.cristian.calculafacil.model.engine

import com.cristian.calculafacil.model.CalcOutcome
import org.junit.Assert.assertTrue
import org.junit.Test

private fun CalcOutcome.values(): List<String> =
    (this as CalcOutcome.Success).rows.map { it.value }

private fun CalcOutcome.isError(): Boolean = this is CalcOutcome.Error
private fun input(vararg pairs: Pair<String, Double>) = mapOf(*pairs)

class SalaryEngineTest {
    @Test
    fun `monthly conversion`() {
        val o = SalaryEngine.calculate(input("monthly" to 2200.0))
        assertTrue(o.values().contains("R$ 10,00"))
        assertTrue(o.values().contains("R$ 73,33"))
    }

    @Test
    fun `zero salary`() {
        val o = SalaryEngine.calculate(input("monthly" to 0.0))
        assertTrue(o.values().contains("R$ 0,00"))
    }

    @Test
    fun `negative error`() {
        assertTrue(SalaryEngine.calculate(input("monthly" to -1.0)).isError())
    }
}

class OvertimeEngineTest {
    @Test
    fun `fifty percent`() {
        val o = OvertimeEngine.calculate(input("hourly" to 30.0, "pct" to 50.0, "count" to 1.0))
        assertTrue(o.values().contains("R$ 45,00"))
    }

    @Test
    fun `zero hours`() {
        val o = OvertimeEngine.calculate(input("hourly" to 30.0, "pct" to 50.0, "count" to 0.0))
        assertTrue(o.values().contains("R$ 0,00"))
    }

    @Test
    fun `negative error`() {
        assertTrue(OvertimeEngine.calculate(input("hourly" to 30.0, "pct" to -1.0, "count" to 1.0)).isError())
    }
}

class VacationEngineTest {
    @Test
    fun `full month vacation`() {
        val o = VacationEngine.calculate(input("salary" to 3000.0, "days" to 30.0))
        assertTrue(o.values().contains("R$ 4.000,00"))
    }

    @Test
    fun `days above 30 error`() {
        assertTrue(VacationEngine.calculate(input("salary" to 3000.0, "days" to 31.0)).isError())
    }
}

class ThirteenthEngineTest {
    @Test
    fun `full year`() {
        val o = ThirteenthEngine.calculate(input("salary" to 3000.0, "months" to 12.0))
        assertTrue(o.values().contains("R$ 3.000,00"))
    }

    @Test
    fun `months above 12 error`() {
        assertTrue(ThirteenthEngine.calculate(input("salary" to 3000.0, "months" to 13.0)).isError())
    }
}

class TerminationEngineTest {
    @Test
    fun `basic estimate`() {
        val o = TerminationEngine.calculate(input("salary" to 3000.0, "days" to 10.0, "months" to 6.0))
        assertTrue(o.values().size == 4)
        assertTrue(o.values().contains("R$ 1.000,00"))
        assertTrue(o.values().contains("R$ 1.500,00"))
    }

    @Test
    fun `days above 30 error`() {
        assertTrue(TerminationEngine.calculate(input("salary" to 3000.0, "days" to 31.0, "months" to 6.0)).isError())
    }

    @Test
    fun `months above 11 accepted`() {
        assertTrue(!TerminationEngine.calculate(input("salary" to 3000.0, "days" to 0.0, "months" to 12.0)).isError())
        assertTrue(!TerminationEngine.calculate(input("salary" to 3000.0, "days" to 0.0, "months" to 18.0)).isError())
        assertTrue(!TerminationEngine.calculate(input("salary" to 3000.0, "days" to 0.0, "months" to 24.0)).isError())
        assertTrue(!TerminationEngine.calculate(input("salary" to 3000.0, "days" to 0.0, "months" to 36.0)).isError())
    }

    @Test
    fun `months above 11 proportional vacation`() {
        val twelve = TerminationEngine.calculate(input("salary" to 3000.0, "days" to 0.0, "months" to 12.0))
        val eighteen = TerminationEngine.calculate(input("salary" to 3000.0, "days" to 0.0, "months" to 18.0))
        val twentyFour = TerminationEngine.calculate(input("salary" to 3000.0, "days" to 0.0, "months" to 24.0))
        val thirtySix = TerminationEngine.calculate(input("salary" to 3000.0, "days" to 0.0, "months" to 36.0))

        assertTrue(twelve.values().contains("R$ 3.000,00"))
        assertTrue(eighteen.values().contains("R$ 4.500,00"))
        assertTrue(twentyFour.values().contains("R$ 6.000,00"))
        assertTrue(thirtySix.values().contains("R$ 9.000,00"))
    }

    @Test
    fun `months up to 120 accepted`() {
        assertTrue(!TerminationEngine.calculate(input("salary" to 3000.0, "days" to 0.0, "months" to 12.0)).isError())
        assertTrue(!TerminationEngine.calculate(input("salary" to 3000.0, "days" to 0.0, "months" to 36.0)).isError())
        assertTrue(!TerminationEngine.calculate(input("salary" to 3000.0, "days" to 0.0, "months" to 60.0)).isError())
        assertTrue(!TerminationEngine.calculate(input("salary" to 3000.0, "days" to 0.0, "months" to 120.0)).isError())
    }

    @Test
    fun `months above 120 error`() {
        assertTrue(TerminationEngine.calculate(input("salary" to 3000.0, "days" to 0.0, "months" to 121.0)).isError())
    }
}