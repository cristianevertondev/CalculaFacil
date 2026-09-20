package com.cristian.calculafacil.model.engine

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BeachAccess
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

object VacationEngine : CalculatorEngine {
    override val id = "vacation"
    override val titleRes = R.string.calc_vacation_title
    override val descriptionRes = R.string.calc_vacation_desc
    override val icon = Icons.Filled.BeachAccess
    override val category = Category.Labor

    override val fields = listOf(
        CalcField("salary", R.string.calc_vacation_field_salary, FieldType.Money),
        CalcField("days", R.string.calc_vacation_field_days, FieldType.Integer),
    )

    override fun calculate(values: Map<String, Double>): CalcOutcome {
        val salary = values["salary"] ?: return CalcOutcome.Error(R.string.error_required)
        val days = values["days"] ?: return CalcOutcome.Error(R.string.error_required)
        if (!Validation.requirePositive(salary)) return CalcOutcome.Error(R.string.error_invalid_number)
        if (!Validation.requireStrictlyPositive(days) || days > 30) {
            return CalcOutcome.Error(R.string.error_invalid_number)
        }

        val base = Labor.vacationBase(salary, days)
        val third = Labor.vacationThird(base)
        val total = base + third
        return CalcOutcome.Success(
            rows = listOf(
                ResultRow(R.string.calc_vacation_total, Format.money(total)),
                ResultRow(R.string.calc_vacation_third, Format.money(third)),
            ),
        )
    }
}