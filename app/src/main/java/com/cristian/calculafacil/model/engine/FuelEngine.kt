package com.cristian.calculafacil.model.engine

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalGasStation
import com.cristian.calculafacil.R
import com.cristian.calculafacil.calculation.Format
import com.cristian.calculafacil.calculation.Validation
import com.cristian.calculafacil.calculation.Vehicle
import com.cristian.calculafacil.model.CalcField
import com.cristian.calculafacil.model.CalcOutcome
import com.cristian.calculafacil.model.CalculatorEngine
import com.cristian.calculafacil.model.Category
import com.cristian.calculafacil.model.FieldType
import com.cristian.calculafacil.model.ResultRow

object FuelEngine : CalculatorEngine {
    override val id = "fuel"
    override val titleRes = R.string.calc_fuel_title
    override val descriptionRes = R.string.calc_fuel_desc
    override val icon = Icons.Filled.LocalGasStation
    override val category = Category.Vehicle

    override val fields = listOf(
        CalcField("distance", R.string.calc_fuel_field_distance, FieldType.Number),
        CalcField("consumption", R.string.calc_fuel_field_consumption, FieldType.Number),
        CalcField("price", R.string.calc_fuel_field_price, FieldType.Money),
    )

    override fun calculate(values: Map<String, Double>): CalcOutcome {
        val distance = values["distance"] ?: return CalcOutcome.Error(R.string.error_required)
        val consumption = values["consumption"] ?: return CalcOutcome.Error(R.string.error_required)
        val price = values["price"] ?: return CalcOutcome.Error(R.string.error_required)
        if (!Validation.requirePositive(distance)) return CalcOutcome.Error(R.string.error_invalid_number)
        if (!Validation.requireStrictlyPositive(consumption)) return CalcOutcome.Error(R.string.error_invalid_number)
        if (!Validation.requirePositive(price)) return CalcOutcome.Error(R.string.error_invalid_number)

        val liters = Vehicle.litersNeeded(distance, consumption)
        val cost = Vehicle.tripCost(distance, consumption, price)
        return CalcOutcome.Success(
            rows = listOf(
                ResultRow(R.string.calc_fuel_cost, Format.money(cost)),
                ResultRow(R.string.calc_fuel_liters, Format.number(liters, 2), suffixRes = R.string.suffix_liters),
            ),
        )
    }
}