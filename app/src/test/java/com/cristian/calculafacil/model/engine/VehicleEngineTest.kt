package com.cristian.calculafacil.model.engine

import com.cristian.calculafacil.model.CalcOutcome
import org.junit.Assert.assertTrue
import org.junit.Test

private fun CalcOutcome.values(): List<String> =
    (this as CalcOutcome.Success).rows.map { it.value }

private fun CalcOutcome.suffixRes(): List<Int> =
    (this as CalcOutcome.Success).rows.mapNotNull { it.suffixRes }

private fun CalcOutcome.isError(): Boolean = this is CalcOutcome.Error
private fun input(vararg pairs: Pair<String, Double>) = mapOf(*pairs)

class FuelEngineTest {
    @Test
    fun `trip cost`() {
        val o = FuelEngine.calculate(input("distance" to 100.0, "consumption" to 10.0, "price" to 5.0))
        assertTrue(o.values().contains("R$ 50,00"))
        assertTrue(o.values().contains("10"))
        assertTrue(o.suffixRes().contains(com.cristian.calculafacil.R.string.suffix_liters))
    }

    @Test
    fun `zero consumption error`() {
        assertTrue(FuelEngine.calculate(input("distance" to 100.0, "consumption" to 0.0, "price" to 5.0)).isError())
    }
}

class ConsumptionEngineTest {
    @Test
    fun `km per liter`() {
        val o = ConsumptionEngine.calculate(input("km" to 250.0, "liters" to 20.0))
        assertTrue(o.values().contains("12,5"))
        assertTrue(o.suffixRes().contains(com.cristian.calculafacil.R.string.suffix_km_per_l))
    }

    @Test
    fun `zero liters error`() {
        assertTrue(ConsumptionEngine.calculate(input("km" to 250.0, "liters" to 0.0)).isError())
    }
}

class SplitEngineTest {
    @Test
    fun `split three people with tip`() {
        val o = SplitEngine.calculate(input("total" to 100.0, "people" to 3.0, "tip" to 10.0))
        assertTrue(o.values().contains("R$ 110,00"))
    }

    @Test
    fun `zero people error`() {
        assertTrue(SplitEngine.calculate(input("total" to 100.0, "people" to 0.0, "tip" to 10.0)).isError())
    }

    @Test
    fun `negative tip error`() {
        assertTrue(SplitEngine.calculate(input("total" to 100.0, "people" to 2.0, "tip" to -5.0)).isError())
    }
}