package com.cristian.calculafacil.calculation

import org.junit.Assert.assertEquals
import org.junit.Test

class ProportionTest {

    @Test
    fun `rule of three`() {
        assertEquals(20.0, Proportion.ruleOfThree(2.0, 10.0, 4.0), 1e-9)
    }

    @Test
    fun `solve for x`() {
        assertEquals(6.0, Proportion.solveForX(4.0, 8.0, 3.0), 1e-9)
    }

    @Test
    fun `fractional result`() {
        assertEquals(2.5, Proportion.ruleOfThree(4.0, 10.0, 1.0), 1e-9)
    }

    @Test
    fun `b zero`() {
        assertEquals(0.0, Proportion.ruleOfThree(2.0, 0.0, 4.0), 1e-9)
    }

    @Test
    fun `c zero`() {
        assertEquals(0.0, Proportion.ruleOfThree(2.0, 5.0, 0.0), 1e-9)
    }

    @Test
    fun `negative values`() {
        assertEquals(-20.0, Proportion.ruleOfThree(2.0, 10.0, -4.0), 1e-9)
    }
}