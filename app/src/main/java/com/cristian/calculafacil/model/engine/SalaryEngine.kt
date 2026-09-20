package com.cristian.calculafacil.model.engine

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Payments
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

object SalaryEngine : CalculatorEngine {
    override val id = "salary"
    override val titleRes = R.string.calc_salary_title
    override val descriptionRes = R.string.calc_salary_desc
    override val icon = Icons.Filled.Payments
    override val category = Category.Labor

    override val fields = listOf(
        CalcField("monthly", R.string.calc_salary_field_month, FieldType.Money),
    )

    override fun calculate(values: Map<String, Double>): CalcOutcome {
        val monthly = values["monthly"] ?: return CalcOutcome.Error(R.string.error_required)
        if (!Validation.requirePositive(monthly)) return CalcOutcome.Error(R.string.error_invalid_number)

        val hourly = Labor.hourlyFromMonthly(monthly)
        val daily = Labor.dailyFromMonthly(monthly)
        return CalcOutcome.Success(
            rows = listOf(
                ResultRow(R.string.calc_salary_hourly, Format.money(hourly)),
                ResultRow(R.string.calc_salary_daily, Format.money(daily)),
                ResultRow(R.string.calc_salary_monthly, Format.money(monthly)),
            ),
        )
    }
}