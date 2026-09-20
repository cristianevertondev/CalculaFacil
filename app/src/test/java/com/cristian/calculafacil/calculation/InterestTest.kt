package com.cristian.calculafacil.calculation

import org.junit.Assert.assertEquals
import org.junit.Test

class InterestTest {

    @Test
    fun `simple interest`() {
        assertEquals(100.0, Interest.simpleInterest(1000.0, 2.0, 5.0), 1e-9)
    }

    @Test
    fun `simple interest zero rate`() {
        assertEquals(0.0, Interest.simpleInterest(1000.0, 0.0, 5.0), 1e-9)
    }

    @Test
    fun `simple amount`() {
        assertEquals(1100.0, Interest.simpleAmount(1000.0, 2.0, 5.0), 1e-9)
    }

    @Test
    fun `compound amount`() {
        assertEquals(1104.081, Interest.compoundAmount(1000.0, 2.0, 5.0), 1e-3)
    }

    @Test
    fun `compound interest`() {
        assertEquals(104.081, Interest.compoundInterest(1000.0, 2.0, 5.0), 1e-3)
    }

    @Test
    fun `compound zero months`() {
        assertEquals(1000.0, Interest.compoundAmount(1000.0, 5.0, 0.0), 1e-9)
    }

    @Test
    fun `price installment zero rate equals principal over months`() {
        assertEquals(200.0, Interest.priceInstallment(1000.0, 0.0, 5.0), 1e-6)
    }

    @Test
    fun `price installment positive rate`() {
        // priceInstallment(1000, 2% a.m., 12) ≈ 94.56
        assertEquals(94.56, Interest.priceInstallment(1000.0, 2.0, 12.0), 0.01)
    }

    @Test
    fun `sac first installment`() {
        // amort = 2000/10 = 200 ; juros = 2000*0.01 = 20 → 220
        assertEquals(220.0, Interest.sacFirstInstallment(2000.0, 1.0, 10.0), 1e-6)
    }

    @Test
    fun `sac last installment`() {
        // amort = 200 ; juros sobre residual = 200*0.01 = 2 → 202
        assertEquals(202.0, Interest.sacLastInstallment(2000.0, 1.0, 10.0), 1e-6)
    }

    @Test
    fun `price total`() {
        assertEquals(94.56 * 12, Interest.priceTotal(1000.0, 2.0, 12.0), 0.2)
    }

    @Test
    fun `sac total zero rate equals principal`() {
        assertEquals(2000.0, Interest.sacTotal(2000.0, 0.0, 10.0), 1e-6)
    }
}