package com.cristian.calculafacil.model.engine

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
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

object IncreaseEngine : CalculatorEngine {
    override val id = "increase"
    override val titleRes = R.string.calc_increase_title
    override val descriptionRes = R.string.calc_increase_desc
    override val icon = Icons.AutoMirrored.Filled.TrendingUp
    override val category = Category.Finance

    override val fields = listOf(
        CalcField("value", R.string.calc_increase_field_value, FieldType.Money),
        CalcField("pct", R.string.calc_increase_field_pct, FieldType.Percent),
    )

    override fun calculate(values: Map<String, Double>): CalcOutcome {
        val value = values["value"] ?: return CalcOutcome.Error(R.string.error_required)
        val pct = values["pct"] ?: return CalcOutcome.Error(R.string.error_required)
        if (!Validation.requirePositive(value)) return CalcOutcome.Error(R.string.error_invalid_number)
        if (!Validation.requirePositive(pct)) return CalcOutcome.Error(R.string.error_invalid_number)

        val amount = Percentage.increaseAmount(value, pct)
        val final = Percentage.applyIncrease(value, pct)
        return CalcOutcome.Success(
            rows = listOf(
                ResultRow(R.string.calc_increase_final, Format.money(final)),
                ResultRow(R.string.calc_increase_amount, Format.money(amount)),
            ),
        )
    }
}