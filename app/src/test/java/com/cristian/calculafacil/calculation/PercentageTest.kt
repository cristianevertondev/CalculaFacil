package com.cristian.calculafacil.calculation

import org.junit.Assert.assertEquals
import org.junit.Test

class PercentageTest {

    @Test
    fun `percent of value`() {
        assertEquals(25.0, Percentage.percentOf(500.0, 5.0), 1e-9)
    }

    @Test
    fun `percent of zero value`() {
        assertEquals(0.0, Percentage.percentOf(0.0, 10.0), 1e-9)
    }

    @Test
    fun `percent of zero percent`() {
        assertEquals(0.0, Percentage.percentOf(100.0, 0.0), 1e-9)
    }

    @Test
    fun `percent fractional`() {
        assertEquals(1.25, Percentage.percentOf(250.0, 0.5), 1e-9)
    }

    @Test
    fun `percent over 100`() {
        assertEquals(150.0, Percentage.percentOf(100.0, 150.0), 1e-9)
    }

    @Test
    fun `discount final`() {
        assertEquals(80.0, Percentage.applyDiscount(100.0, 20.0), 1e-9)
    }

    @Test
    fun `discount zero`() {
        assertEquals(100.0, Percentage.applyDiscount(100.0, 0.0), 1e-9)
    }

    @Test
    fun `discount hundred`() {
        assertEquals(0.0, Percentage.applyDiscount(100.0, 100.0), 1e-9)
    }

    @Test
    fun `discount amount`() {
        assertEquals(20.0, Percentage.discountAmount(100.0, 20.0), 1e-9)
    }

    @Test
    fun `increase final`() {
        assertEquals(120.0, Percentage.applyIncrease(100.0, 20.0), 1e-9)
    }

    @Test
    fun `increase zero`() {
        assertEquals(100.0, Percentage.applyIncrease(100.0, 0.0), 1e-9)
    }

    @Test
    fun `increase amount`() {
        assertEquals(20.0, Percentage.increaseAmount(100.0, 20.0), 1e-9)
    }

    @Test
    fun `discount fractional`() {
        assertEquals(99.9, Percentage.applyDiscount(200.0, 50.05), 1e-6)
    }
}