package com.cristian.calculafacil.calculation

import org.junit.Assert.assertEquals
import org.junit.Test

class VehicleAndSplitTest {

    @Test
    fun `liters needed`() {
        assertEquals(10.0, Vehicle.litersNeeded(100.0, 10.0), 1e-9)
    }

    @Test
    fun `trip cost`() {
        assertEquals(50.0, Vehicle.tripCost(100.0, 10.0, 5.0), 1e-9)
    }

    @Test
    fun `trip cost zero distance`() {
        assertEquals(0.0, Vehicle.tripCost(0.0, 10.0, 5.0), 1e-9)
    }

    @Test
    fun `consumption km per liter`() {
        assertEquals(12.5, Vehicle.consumptionKmPerLiter(250.0, 20.0), 1e-9)
    }

    @Test
    fun `split total with tip`() {
        assertEquals(110.0, Split.totalWithTip(100.0, 10.0), 1e-9)
    }

    @Test
    fun `split zero tip`() {
        assertEquals(100.0, Split.totalWithTip(100.0, 0.0), 1e-9)
    }

    @Test
    fun `split per person`() {
        assertEquals(36.66, Split.perPerson(100.0, 10.0, 3.0), 0.01)
    }

    @Test
    fun `split one person`() {
        assertEquals(110.0, Split.perPerson(100.0, 10.0, 1.0), 1e-9)
    }
}