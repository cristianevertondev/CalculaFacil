package com.cristian.calculafacil.calculation

/**
 * Conversão de unidades — lógica pura e testável.
 *
 * Cada unidade é uma transformação afim para uma unidade-base da dimensão:
 *   valorNaBase = valor * factor + offset
 * e a conversão entre duas unidades usa:
 *   resultado = (valor * fOrigem + offOrigem - offDestino) / fDestino
 * Temperatura usa offsets (Celsius/Fahrenheit/Kelvin); as demais usam apenas fator.
 */
object UnitConverter {

    data class UnitDef(
        val id: String,
        val factor: Double,
        val offset: Double = 0.0,
    )

    enum class Dimension(val units: List<UnitDef>) {
        LENGTH(
            listOf(
                UnitDef("m", 1.0),
                UnitDef("km", 1000.0),
                UnitDef("cm", 0.01),
                UnitDef("mm", 0.001),
                UnitDef("mi", 1609.344),
                UnitDef("ft", 0.3048),
                UnitDef("in", 0.0254),
            ),
        ),
        MASS(
            listOf(
                UnitDef("kg", 1.0),
                UnitDef("g", 0.001),
                UnitDef("mg", 1e-6),
                UnitDef("lb", 0.45359237),
                UnitDef("oz", 0.028349523),
            ),
        ),
        TEMPERATURE(
            listOf(
                UnitDef("c", 1.0, 0.0),
                UnitDef("f", 5.0 / 9.0, -160.0 / 9.0),
                UnitDef("k", 1.0, -273.15),
            ),
        ),
        VOLUME(
            listOf(
                UnitDef("l", 1.0),
                UnitDef("ml", 0.001),
                UnitDef("gal", 3.785411784),
                UnitDef("cup", 0.24),
            ),
        ),
    }

    fun convert(value: Double, dimension: Dimension, fromId: String, toId: String): Double? {
        val from = dimension.units.firstOrNull { it.id == fromId } ?: return null
        val to = dimension.units.firstOrNull { it.id == toId } ?: return null
        val inBase = value * from.factor + from.offset
        return (inBase - to.offset) / to.factor
    }

    fun unitById(dimension: Dimension, id: String): UnitDef? =
        dimension.units.firstOrNull { it.id == id }
}