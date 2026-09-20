package com.cristian.calculafacil.model.engine

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
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

object TerminationEngine : CalculatorEngine {
    override val id = "termination"
    override val titleRes = R.string.calc_termination_title
    override val descriptionRes = R.string.calc_termination_desc
    override val icon = Icons.Filled.Description
    override val category = Category.Labor

    override val fields = listOf(
        CalcField("salary", R.string.calc_termination_field_salary, FieldType.Money),
        CalcField("days", R.string.calc_termination_field_days, FieldType.Integer),
        CalcField("months", R.string.calc_termination_field_months, FieldType.Integer),
    )

    override fun calculate(values: Map<String, Double>): CalcOutcome {
        val salary = values["salary"] ?: return CalcOutcome.Error(R.string.error_required)
        val days = values["days"] ?: return CalcOutcome.Error(R.string.error_required)
        val months = values["months"] ?: return CalcOutcome.Error(R.string.error_required)
        if (!Validation.requirePositive(salary)) return CalcOutcome.Error(R.string.error_invalid_number)
        if (!Validation.requirePositive(days) || days > 30) return CalcOutcome.Error(R.string.error_invalid_number)
        if (!Validation.requirePositive(months) || months > 120) return CalcOutcome.Error(R.string.error_invalid_number)

        val saldo = Labor.salaryBalance(salary, days)
        val ferias = Labor.proportionalVacation(salary, months)
        val terco = Labor.vacationThird(ferias)
        val total = saldo + ferias + terco
        return CalcOutcome.Success(
            rows = listOf(
                ResultRow(R.string.calc_termination_value_total, Format.money(total)),
                ResultRow(R.string.calc_termination_value_salary, Format.money(saldo)),
                ResultRow(R.string.calc_termination_value_vacation, Format.money(ferias)),
                ResultRow(R.string.calc_termination_value_third, Format.money(terco)),
            ),
        )
    }
}