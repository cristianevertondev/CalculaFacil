package com.cristian.calculafacil.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CurrencyExchange
import androidx.compose.material.icons.filled.Functions
import androidx.compose.ui.graphics.vector.ImageVector
import com.cristian.calculafacil.R
import com.cristian.calculafacil.calculation.CurrencyConverter
import com.cristian.calculafacil.calculation.StaticCurrencyRates
import com.cristian.calculafacil.calculation.UnitConverter

/** Item exibido na Home (calculadora ou conversor). */
interface ToolItem {
    val id: String
    @get:StringRes val titleRes: Int
    @get:StringRes val descriptionRes: Int
    val icon: ImageVector
    val category: Category
}

/** Opção selecionável (unidade, moeda, dimensão). */
data class ConverterOption(
    val id: String,
    @get:StringRes val labelRes: Int,
)

/** Categoria/dimensão de um conversor de unidades. */
data class ConverterDimension(
    val id: String,
    @get:StringRes val labelRes: Int,
    val units: List<ConverterOption>,
)

/**
 * Conversor (unidades ou moedas). Usa dropdowns de "de/para" em vez de
 * campos numéricos fixos, por isso tem tela própria.
 */
sealed interface Converter : ToolItem {
    /** Dimensões selecionáveis (vazio para moedas, que não têm dimensão). */
    val dimensions: List<ConverterDimension>
    val initialFromId: String
    val initialToId: String

    fun optionsForDimension(dimId: String?): List<ConverterOption>

    /** Converte [value] de [fromId] para [toId]; `null` se inválido. */
    fun convert(value: Double, fromId: String, toId: String): Double?

    /** Resource de rótulo de uma opção (unidade ou moeda), ou `null`. */
    @StringRes fun optionLabelRes(id: String): Int?
}

object UnitsConverter : Converter {
    override val id = "units"
    override val titleRes = R.string.calc_units_title
    override val descriptionRes = R.string.calc_units_desc
    override val icon = Icons.Filled.Functions
    override val category = Category.Converter

    private val length = ConverterDimension(
        "length",
        R.string.unit_dim_length,
        listOf(
            ConverterOption("m", R.string.unit_m),
            ConverterOption("km", R.string.unit_km),
            ConverterOption("cm", R.string.unit_cm),
            ConverterOption("mm", R.string.unit_mm),
            ConverterOption("mi", R.string.unit_mi),
            ConverterOption("ft", R.string.unit_ft),
            ConverterOption("in", R.string.unit_in),
        ),
    )
    private val mass = ConverterDimension(
        "mass",
        R.string.unit_dim_mass,
        listOf(
            ConverterOption("kg", R.string.unit_kg),
            ConverterOption("g", R.string.unit_g),
            ConverterOption("mg", R.string.unit_mg),
            ConverterOption("lb", R.string.unit_lb),
            ConverterOption("oz", R.string.unit_oz),
        ),
    )
    private val temperature = ConverterDimension(
        "temperature",
        R.string.unit_dim_temperature,
        listOf(
            ConverterOption("c", R.string.unit_c),
            ConverterOption("f", R.string.unit_f),
            ConverterOption("k", R.string.unit_k),
        ),
    )
    private val volume = ConverterDimension(
        "volume",
        R.string.unit_dim_volume,
        listOf(
            ConverterOption("l", R.string.unit_l),
            ConverterOption("ml", R.string.unit_ml),
            ConverterOption("gal", R.string.unit_gal),
            ConverterOption("cup", R.string.unit_cup),
        ),
    )

    override val dimensions = listOf(length, mass, temperature, volume)
    override val initialFromId = "m"
    override val initialToId = "km"

    override fun optionsForDimension(dimId: String?): List<ConverterOption> =
        dimensions.firstOrNull { it.id == dimId }?.units ?: emptyList()

    override fun convert(value: Double, fromId: String, toId: String): Double? {
        // determina a dimensão que contém ambas as unidades
        val dimension = dimensions.firstOrNull {
            it.units.any { u -> u.id == fromId } && it.units.any { u -> u.id == toId }
        } ?: return null
        val dim = when (dimension.id) {
            "length" -> UnitConverter.Dimension.LENGTH
            "mass" -> UnitConverter.Dimension.MASS
            "temperature" -> UnitConverter.Dimension.TEMPERATURE
            "volume" -> UnitConverter.Dimension.VOLUME
            else -> return null
        }
        return UnitConverter.convert(value, dim, fromId, toId)
    }

    override fun optionLabelRes(id: String): Int? {
        val all = dimensions.flatMap { it.units }
        return all.firstOrNull { it.id == id }?.labelRes
    }
}

object CurrencyConverterTool : Converter {
    override val id = "currency"
    override val titleRes = R.string.calc_currency_title
    override val descriptionRes = R.string.calc_currency_desc
    override val icon = Icons.Filled.CurrencyExchange
    override val category = Category.Converter

    private val currencies: List<ConverterOption> =
        CurrencyConverter.supportedCodes.map { code ->
            ConverterOption(code, codeLabelRes(code))
        }

    override val dimensions = emptyList<ConverterDimension>()
    override val initialFromId = "BRL"
    override val initialToId = "USD"

    override fun optionsForDimension(dimId: String?): List<ConverterOption> = currencies

    override fun convert(value: Double, fromId: String, toId: String): Double? =
        CurrencyConverter.convert(value, fromId, toId, StaticCurrencyRates)

    override fun optionLabelRes(id: String): Int? = currencies.firstOrNull { it.id == id }?.labelRes

    private fun codeLabelRes(code: String): Int = when (code) {
        "BRL" -> R.string.currency_brl
        "USD" -> R.string.currency_usd
        "EUR" -> R.string.currency_eur
        "ARS" -> R.string.currency_ars
        "PYG" -> R.string.currency_pyg
        "UYU" -> R.string.currency_uyu
        else -> R.string.currency_brl
    }
}