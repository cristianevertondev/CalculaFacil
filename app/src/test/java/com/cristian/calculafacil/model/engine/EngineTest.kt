package com.cristian.calculafacil.model.engine

import com.cristian.calculafacil.model.CalcOutcome
import org.junit.Assert.assertTrue
import org.junit.Test

/** Helper para montar inputs de teste. */
private fun input(vararg pairs: Pair<String, Double>) = mapOf(*pairs)

private fun CalcOutcome.successRows(): List<String> =
    (this as CalcOutcome.Success).rows.map { it.value }

private fun CalcOutcome.isError(): Boolean = this is CalcOutcome.Error

class PercentEngineTest {
    @Test
    fun `five percent of five hundred`() {
        val o = PercentEngine.calculate(input("value" to 500.0, "pct" to 5.0))
        assertTrue(o.successRows().contains("R$ 25,00"))
    }

    @Test
    fun `zero percent`() {
        val o = PercentEngine.calculate(input("value" to 100.0, "pct" to 0.0))
        assertTrue(o.successRows().contains("R$ 0,00"))
    }

    @Test
    fun `missing value error`() {
        assertTrue(PercentEngine.calculate(input("pct" to 5.0)).isError())
    }

    @Test
    fun `negative value error`() {
        assertTrue(PercentEngine.calculate(input("value" to -1.0, "pct" to 5.0)).isError())
    }
}

class DiscountEngineTest {
    @Test
    fun `discount of twenty percent`() {
        val o = DiscountEngine.calculate(input("price" to 100.0, "pct" to 20.0))
        assertTrue(o.successRows().contains("R$ 80,00"))
        assertTrue(o.successRows().contains("R$ 20,00"))
    }

    @Test
    fun `full discount zero price`() {
        val o = DiscountEngine.calculate(input("price" to 100.0, "pct" to 100.0))
        assertTrue(o.successRows().contains("R$ 0,00"))
    }

    @Test
    fun `over hundred percent error`() {
        assertTrue(DiscountEngine.calculate(input("price" to 100.0, "pct" to 150.0)).isError())
    }
}

class IncreaseEngineTest {
    @Test
    fun `increase twenty percent`() {
        val o = IncreaseEngine.calculate(input("value" to 100.0, "pct" to 20.0))
        assertTrue(o.successRows().contains("R$ 120,00"))
        assertTrue(o.successRows().contains("R$ 20,00"))
    }

    @Test
    fun `zero increase`() {
        val o = IncreaseEngine.calculate(input("value" to 50.0, "pct" to 0.0))
        assertTrue(o.successRows().contains("R$ 50,00"))
    }
}

class Rule3EngineTest {
    @Test
    fun `basic proportion`() {
        val o = Rule3Engine.calculate(input("a" to 2.0, "b" to 10.0, "c" to 4.0))
        assertTrue(o.successRows().contains("20"))
    }

    @Test
    fun `zero divisor error`() {
        assertTrue(Rule3Engine.calculate(input("a" to 0.0, "b" to 10.0, "c" to 4.0)).isError())
    }
}

class SimpleInterestEngineTest {
    @Test
    fun `basic simple interest`() {
        val o = SimpleInterestEngine.calculate(
            input("principal" to 1000.0, "rate" to 2.0, "months" to 5.0),
        )
        assertTrue(o.successRows().contains("R$ 1.100,00"))
        assertTrue(o.successRows().contains("R$ 100,00"))
    }

    @Test
    fun `zero months error`() {
        assertTrue(
            SimpleInterestEngine.calculate(input("principal" to 1000.0, "rate" to 2.0, "months" to 0.0))
                .isError(),
        )
    }
}

class CompoundInterestEngineTest {
    @Test
    fun `basic compound interest`() {
        val o = CompoundInterestEngine.calculate(
            input("principal" to 1000.0, "rate" to 2.0, "months" to 12.0),
        )
        assertTrue(o.successRows().size == 2)
    }

    @Test
    fun `zero principal`() {
        val o = CompoundInterestEngine.calculate(
            input("principal" to 0.0, "rate" to 2.0, "months" to 12.0),
        )
        assertTrue(o.successRows().contains("R$ 0,00"))
    }
}