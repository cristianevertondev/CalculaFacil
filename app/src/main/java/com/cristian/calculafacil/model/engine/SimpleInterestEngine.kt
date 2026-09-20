package com.cristian.calculafacil.model.engine

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Timeline
import com.cristian.calculafacil.R
import com.cristian.calculafacil.calculation.Format
import com.cristian.calculafacil.calculation.Interest
import com.cristian.calculafacil.calculation.Validation
import com.cristian.calculafacil.model.CalcField
import com.cristian.calculafacil.model.CalcOutcome
import com.cristian.calculafacil.model.CalculatorEngine
import com.cristian.calculafacil.model.Category
import com.cristian.calculafacil.model.FieldType
import com.cristian.calculafacil.model.ResultRow

object SimpleInterestEngine : CalculatorEngine {
    override val id = "simple_interest"
    override val titleRes = R.string.calc_simple_title
    override val descriptionRes = R.string.calc_simple_desc
    override val icon = Icons.Filled.Timeline
    override val category = Category.Finance

    override val fields = listOf(
        CalcField("principal", R.string.calc_simple_field_principal, FieldType.Money),
        CalcField("rate", R.string.calc_simple_field_rate, FieldType.Percent),
        CalcField("months", R.string.calc_simple_field_months, FieldType.Integer),
    )

    override fun calculate(values: Map<String, Double>): CalcOutcome {
        val principal = values["principal"] ?: return CalcOutcome.Error(R.string.error_required)
        val rate = values["rate"] ?: return CalcOutcome.Error(R.string.error_required)
        val months = values["months"] ?: return CalcOutcome.Error(R.string.error_required)
        if (!Validation.requirePositive(principal)) return CalcOutcome.Error(R.string.error_invalid_number)
        if (!Validation.requirePositive(rate)) return CalcOutcome.Error(R.string.error_invalid_number)
        if (!Validation.requireStrictlyPositive(months)) return CalcOutcome.Error(R.string.error_invalid_number)

        val interest = Interest.simpleInterest(principal, rate, months)
        val total = Interest.simpleAmount(principal, rate, months)
        return CalcOutcome.Success(
            rows = listOf(
                ResultRow(R.string.calc_simple_total, Format.money(total)),
                ResultRow(R.string.calc_simple_interest, Format.money(interest)),
            ),
        )
    }
}