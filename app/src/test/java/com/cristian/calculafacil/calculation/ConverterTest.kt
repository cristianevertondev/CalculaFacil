package com.cristian.calculafacil.calculation

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class UnitConverterTest {

    @Test
    fun `meters to kilometers`() {
        assertEquals(1.5, UnitConverter.convert(1500.0, UnitConverter.Dimension.LENGTH, "m", "km")!!, 1e-9)
    }

    @Test
    fun `kilometers to meters`() {
        assertEquals(5000.0, UnitConverter.convert(5.0, UnitConverter.Dimension.LENGTH, "km", "m")!!, 1e-9)
    }

    @Test
    fun `kilograms to grams`() {
        assertEquals(2500.0, UnitConverter.convert(2.5, UnitConverter.Dimension.MASS, "kg", "g")!!, 1e-9)
    }

    @Test
    fun `celsius to fahrenheit`() {
        assertEquals(32.0, UnitConverter.convert(0.0, UnitConverter.Dimension.TEMPERATURE, "c", "f")!!, 1e-6)
    }

    @Test
    fun `fahrenheit to celsius`() {
        assertEquals(0.0, UnitConverter.convert(32.0, UnitConverter.Dimension.TEMPERATURE, "f", "c")!!, 1e-6)
    }

    @Test
    fun `celsius to kelvin`() {
        assertEquals(273.15, UnitConverter.convert(0.0, UnitConverter.Dimension.TEMPERATURE, "c", "k")!!, 1e-6)
    }

    @Test
    fun `liters to milliliters`() {
        assertEquals(1000.0, UnitConverter.convert(1.0, UnitConverter.Dimension.VOLUME, "l", "ml")!!, 1e-9)
    }

    @Test
    fun `same unit returns same value`() {
        assertEquals(42.0, UnitConverter.convert(42.0, UnitConverter.Dimension.LENGTH, "m", "m")!!, 1e-9)
    }

    @Test
    fun `unknown unit returns null`() {
        assertNull(UnitConverter.convert(1.0, UnitConverter.Dimension.LENGTH, "m", "xxx"))
    }

    @Test
    fun `negative value temperature`() {
        assertEquals(-40.0, UnitConverter.convert(-40.0, UnitConverter.Dimension.TEMPERATURE, "c", "f")!!, 1e-6)
    }
}

class CurrencyConverterTest {

    private val rates = StaticCurrencyRates

    @Test
    fun `brl to usd`() {
        // 100 BRL = 100 / 5.20 USD ≈ 19.23
        assertEquals(19.23, CurrencyConverter.convert(100.0, "BRL", "USD", rates)!!, 0.01)
    }

    @Test
    fun `usd to brl`() {
        // 10 USD = 10 * 5.20 BRL = 52
        assertEquals(52.0, CurrencyConverter.convert(10.0, "USD", "BRL", rates)!!, 1e-9)
    }

    @Test
    fun `same currency returns value`() {
        assertEquals(100.0, CurrencyConverter.convert(100.0, "BRL", "BRL", rates)!!, 1e-9)
    }

    @Test
    fun `usd to eur`() {
        // 1 USD = 5.20/5.60 EUR ≈ 0.9286
        assertEquals(0.9286, CurrencyConverter.convert(1.0, "USD", "EUR", rates)!!, 0.001)
    }

    @Test
    fun `zero value`() {
        assertEquals(0.0, CurrencyConverter.convert(0.0, "USD", "BRL", rates)!!, 1e-9)
    }

    @Test
    fun `unknown currency null`() {
        assertNull(CurrencyConverter.convert(1.0, "XXX", "USD", rates))
    }

    @Test
    fun `source without rate for a currency returns null`() {
        // Simula uma fonte futura que não possui a taxa de uma moeda suportada.
        val missingRateSource = object : CurrencyConverter.CurrencyRateSource {
            override fun rate(currencyCode: String): Double? =
                if (currencyCode == "USD") null else 1.0
        }
        assertNull(CurrencyConverter.convert(100.0, "USD", "BRL", missingRateSource))
        assertNull(CurrencyConverter.convert(100.0, "BRL", "USD", missingRateSource))
    }

    @Test
    fun `source returning zero rate is guarded`() {
        val badSource = object : CurrencyConverter.CurrencyRateSource {
            override fun rate(currencyCode: String): Double? = 0.0
        }
        assertNull(CurrencyConverter.convert(1.0, "USD", "BRL", badSource))
    }

    @Test
    fun `format code`() {
        assertEquals("5,20 USD", CurrencyConverter.formatCode(5.2, "USD"))
    }
}