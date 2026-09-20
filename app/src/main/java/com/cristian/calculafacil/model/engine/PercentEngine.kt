package com.cristian.calculafacil.model.engine

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Percent
import com.cristian.calculafacil.R
import com.cristian.calculafacil.calculation.Format
import com.cristian.calculafacil.calculation.Percentage
import com.cristian.calculafacil.calculation.Validation
import com.cristian.calculafacil.model.CalcField
import com.cristian.calculafacil.model.CalcOutcome
import com.cristian.calculafacil.model.CalculatorEngine
import com.cristian.calculafacil.model.Category
import com.cristian.calculafacil.model.FieldType
import com.cristian.calculafacil.model.ResultRow

object PercentEngine : CalculatorEngine {
    override val id = "percent"
    override val titleRes = R.string.calc_percent_title
    override val descriptionRes = R.string.calc_percent_desc
    override val icon = Icons.Filled.Percent
    override val category = Category.Finance

    override val fields = listOf(
        CalcField("value", R.string.calc_percent_field_value, FieldType.Money),
        CalcField("pct", R.string.calc_percent_field_pct, FieldType.Percent),
    )

    override fun calculate(values: Map<String, Double>): CalcOutcome {
        val value = values["value"] ?: return CalcOutcome.Error(R.string.error_required)
        val pct = values["pct"] ?: return CalcOutcome.Error(R.string.error_required)
        if (!Validation.requirePositive(value)) return CalcOutcome.Error(R.string.error_invalid_number)
        if (!Validation.requirePositive(pct)) return CalcOutcome.Error(R.string.error_invalid_number)

        val result = Percentage.percentOf(value, pct)
        return CalcOutcome.Success(
            titleRes = R.string.calc_percent_result,
            rows = listOf(ResultRow(R.string.calc_percent_result, Format.money(result))),
        )
    }
}