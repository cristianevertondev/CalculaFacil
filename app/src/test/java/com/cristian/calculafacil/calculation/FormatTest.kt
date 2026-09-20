package com.cristian.calculafacil.calculation

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class FormatTest {

    @Test
    fun `money brazilian`() {
        assertEquals("R$ 1.234,56", Format.money(1234.56))
    }

    @Test
    fun `money zero`() {
        assertEquals("R$ 0,00", Format.money(0.0))
    }

    @Test
    fun `money negative`() {
        assertEquals("-R$ 10,50", Format.money(-10.50))
    }

    @Test
    fun `number decimal`() {
        assertEquals("12,34", Format.number(12.34))
    }

    @Test
    fun `number zero`() {
        assertEquals("0", Format.number(0.0))
    }

    @Test
    fun `number many decimals`() {
        assertEquals("1,2346", Format.number(1.23456, 4))
    }

    @Test
    fun `integer`() {
        assertEquals("42", Format.integer(42.0))
    }

    @Test
    fun `integer rounds to nearest`() {
        assertEquals("43", Format.integer(42.7))
    }

    @Test
    fun `number very large`() {
        val s = Format.number(1e15)
        assertTrue(s.isNotBlank())
    }

    @Test
    fun `number groups thousands`() {
        assertEquals("1.234.567", Format.number(1234567.0))
    }

    @Test
    fun `number keeps decimals with grouping`() {
        // 1234,56789 arredondado a 4 casas = 1234,5679
        assertEquals("1.234,5679", Format.number(1234.56789, 4))
    }

    @Test
    fun `number below thousand no grouping`() {
        assertEquals("987,65", Format.number(987.65))
    }
}