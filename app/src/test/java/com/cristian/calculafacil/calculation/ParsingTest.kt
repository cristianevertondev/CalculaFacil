package com.cristian.calculafacil.calculation

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ParsingTest {

    @Test
    fun `integer plain`() {
        assertEquals(123.0, Parsing.parseDecimal("123"))
    }

    @Test
    fun `integer with spaces`() {
        assertEquals(123.0, Parsing.parseDecimal("  123  "))
    }

    @Test
    fun `dot decimal`() {
        assertEquals(12.5, Parsing.parseDecimal("12.5"))
    }

    @Test
    fun `comma decimal`() {
        assertEquals(12.5, Parsing.parseDecimal("12,5"))
    }

    @Test
    fun `brazilian thousands with comma decimal`() {
        assertEquals(1234.56, Parsing.parseDecimal("1.234,56"))
    }

    @Test
    fun `brazilian thousands without comma`() {
        assertEquals(1234567.0, Parsing.parseDecimal("1.234.567"))
    }

    @Test
    fun `currency symbol`() {
        assertEquals(9.99, Parsing.parseDecimal("R$ 9,99"))
    }

    @Test
    fun `percent symbol`() {
        assertEquals(15.0, Parsing.parseDecimal("15 %"))
    }

    @Test
    fun `negative number`() {
        assertEquals(-3.0, Parsing.parseDecimal("-3"))
    }

    @Test
    fun `negative decimal comma`() {
        assertEquals(-2.5, Parsing.parseDecimal("-2,5"))
    }

    @Test
    fun `blank is invalid`() {
        assertNull(Parsing.parseDecimal(""))
    }

    @Test
    fun `spaces only invalid`() {
        assertNull(Parsing.parseDecimal("   "))
    }

    @Test
    fun `letters invalid`() {
        assertNull(Parsing.parseDecimal("abc"))
    }

    @Test
    fun `multiple commas invalid`() {
        assertNull(Parsing.parseDecimal("1,2,3"))
    }

    @Test
    fun `us format comma thousands dot decimal`() {
        assertEquals(1234.56, Parsing.parseDecimal("1,234.56"))
    }

    @Test
    fun `zero is valid`() {
        assertEquals(0.0, Parsing.parseDecimal("0"))
    }

    @Test
    fun `isWhole true`() {
        org.junit.Assert.assertTrue(Parsing.isWhole(5.0))
    }

    @Test
    fun `isWhole false`() {
        org.junit.Assert.assertFalse(Parsing.isWhole(5.5))
    }

    @Test
    fun `isWhole zero`() {
        org.junit.Assert.assertTrue(Parsing.isWhole(0.0))
    }
}