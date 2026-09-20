package com.cristian.calculafacil.model.engine

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cake
import com.cristian.calculafacil.R
import com.cristian.calculafacil.calculation.Format
import com.cristian.calculafacil.calculation.Labor
import com.cristian.calculafacil.calculation.Validation
import com.cristian.calculafacil.model.CalcField
import com.cristian.calculafacil.model.CalcOutcome
import com.cristian.calculafacil.model.CalculatorEngine
import com.cristian.calculafacil.model.Category
import com.cristian.calculafacil.model.FieldType
import com.cristian.calculafacil.model.ResultRow

object ThirteenthEngine : CalculatorEngine {
    override val id = "thirteenth"
    override val titleRes = R.string.calc_thirteenth_title
    override val descriptionRes = R.string.calc_thirteenth_desc
    override val icon = Icons.Filled.Cake
    override val category = Category.Labor

    override val fields = listOf(
        CalcField("salary", R.string.calc_thirteenth_field_salary, FieldType.Money),
        CalcField("months", R.string.calc_thirteenth_field_months, FieldType.Integer),
    )

    override fun calculate(values: Map<String, Double>): CalcOutcome {
        val salary = values["salary"] ?: return CalcOutcome.Error(R.string.error_required)
        val months = values["months"] ?: return CalcOutcome.Error(R.string.error_required)
        if (!Validation.requirePositive(salary)) return CalcOutcome.Error(R.string.error_invalid_number)
        if (!Validation.requirePositive(months) || months > 12) {
            return CalcOutcome.Error(R.string.error_invalid_number)
        }

        val value = Labor.thirteenth(salary, months)
        return CalcOutcome.Success(
            titleRes = R.string.calc_thirteenth_value,
            rows = listOf(ResultRow(R.string.calc_thirteenth_value, Format.money(value))),
        )
    }
}