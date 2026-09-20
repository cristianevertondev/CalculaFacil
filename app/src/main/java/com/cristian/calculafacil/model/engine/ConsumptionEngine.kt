package com.cristian.calculafacil.model.engine

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Speed
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

object ConsumptionEngine : CalculatorEngine {
    override val id = "consumption"
    override val titleRes = R.string.calc_consumption_title
    override val descriptionRes = R.string.calc_consumption_desc
    override val icon = Icons.Filled.Speed
    override val category = Category.Vehicle

    override val fields = listOf(
        CalcField("km", R.string.calc_consumption_field_km, FieldType.Number),
        CalcField("liters", R.string.calc_consumption_field_liters, FieldType.Number),
    )

    override fun calculate(values: Map<String, Double>): CalcOutcome {
        val km = values["km"] ?: return CalcOutcome.Error(R.string.error_required)
        val liters = values["liters"] ?: return CalcOutcome.Error(R.string.error_required)
        if (!Validation.requirePositive(km)) return CalcOutcome.Error(R.string.error_invalid_number)
        if (!Validation.requireStrictlyPositive(liters)) return CalcOutcome.Error(R.string.error_invalid_number)

        val value = Vehicle.consumptionKmPerLiter(km, liters)
        return CalcOutcome.Success(
            titleRes = R.string.calc_consumption_value,
            rows = listOf(
                ResultRow(R.string.calc_consumption_value, Format.number(value, 2), suffixRes = R.string.suffix_km_per_l),
            ),
        )
    }
}