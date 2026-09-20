package com.cristian.calculafacil.model.engine

import com.cristian.calculafacil.model.CalcOutcome
import org.junit.Assert.assertTrue
import org.junit.Test

private fun CalcOutcome.values(): List<String> =
    (this as CalcOutcome.Success).rows.map { it.value }

private fun CalcOutcome.isError(): Boolean = this is CalcOutcome.Error

class InstallmentEngineTest {
    @Test
    fun `zero rate splits equally`() {
        val o = InstallmentEngine.calculate(input("amount" to 1000.0, "rate" to 0.0, "count" to 4.0))
        val v = o.values()
        assertTrue(v.contains("R$ 250,00"))
        assertTrue(v.contains("R$ 1.000,00"))
    }

    @Test
    fun `with interest`() {
        val o = InstallmentEngine.calculate(input("amount" to 1000.0, "rate" to 2.0, "count" to 6.0))
        assertTrue(o.values().size == 3)
    }

    @Test
    fun `zero count error`() {
        assertTrue(InstallmentEngine.calculate(input("amount" to 1000.0, "rate" to 2.0, "count" to 0.0)).isError())
    }

    @Test
    fun `negative amount error`() {
        assertTrue(InstallmentEngine.calculate(input("amount" to -5.0, "rate" to 2.0, "count" to 3.0)).isError())
    }
}

class FinancingEngineTest {
    @Test
    fun `produces all result rows`() {
        val o = FinancingEngine.calculate(input("value" to 50000.0, "rate" to 1.5, "months" to 60.0))
        assertTrue(o.values().size == 5)
    }

    @Test
    fun `zero rate`() {
        val o = FinancingEngine.calculate(input("value" to 1200.0, "rate" to 0.0, "months" to 12.0))
        val v = o.values()
        assertTrue(v.contains("R$ 100,00"))
    }

    @Test
    fun `zero months error`() {
        assertTrue(FinancingEngine.calculate(input("value" to 50000.0, "rate" to 1.5, "months" to 0.0)).isError())
    }

    @Test
    fun `negative rate error`() {
        assertTrue(FinancingEngine.calculate(input("value" to 50000.0, "rate" to -1.0, "months" to 12.0)).isError())
    }
}

private fun input(vararg pairs: Pair<String, Double>) = mapOf(*pairs)