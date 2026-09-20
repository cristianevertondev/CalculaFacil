package com.cristian.calculafacil.calculation

import org.junit.Assert.assertEquals
import org.junit.Test

class LaborTest {

    @Test
    fun `hourly from monthly base 220`() {
        assertEquals(10.0, Labor.hourlyFromMonthly(2200.0), 1e-9)
    }

    @Test
    fun `daily from monthly base 30`() {
        assertEquals(100.0, Labor.dailyFromMonthly(3000.0), 1e-9)
    }

    @Test
    fun `overtime 50 percent`() {
        assertEquals(45.0, Labor.overtimeValue(30.0, 50.0, 1.0), 1e-9)
    }

    @Test
    fun `overtime 100 percent`() {
        assertEquals(60.0, Labor.overtimeValue(30.0, 100.0, 1.0), 1e-9)
    }

    @Test
    fun `overtime multiple hours`() {
        assertEquals(180.0, Labor.overtimeValue(30.0, 50.0, 4.0), 1e-9)
    }

    @Test
    fun `vacation base`() {
        assertEquals(2000.0, Labor.vacationBase(3000.0, 20.0), 1e-9)
    }

    @Test
    fun `vacation third`() {
        assertEquals(666.666, Labor.vacationThird(2000.0), 1e-3)
    }

    @Test
    fun `vacation total includes third`() {
        val total = Labor.vacationTotal(3000.0, 30.0)
        assertEquals(3000.0 + 1000.0, total, 1e-6)
    }

    @Test
    fun `thirteenth full year`() {
        assertEquals(3000.0, Labor.thirteenth(3000.0, 12.0), 1e-9)
    }

    @Test
    fun `thirteenth six months`() {
        assertEquals(1500.0, Labor.thirteenth(3000.0, 6.0), 1e-9)
    }

    @Test
    fun `salary balance days`() {
        assertEquals(1000.0, Labor.salaryBalance(3000.0, 10.0), 1e-9)
    }

    @Test
    fun `proportional vacation`() {
        assertEquals(2500.0, Labor.proportionalVacation(3000.0, 10.0), 1e-9)
    }
}