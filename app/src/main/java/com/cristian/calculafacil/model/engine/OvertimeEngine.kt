package com.cristian.calculafacil.model.engine

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
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

object OvertimeEngine : CalculatorEngine {
    override val id = "overtime"
    override val titleRes = R.string.calc_overtime_title
    override val descriptionRes = R.string.calc_overtime_desc
    override val icon = Icons.Filled.Schedule
    override val category = Category.Labor

    override val fields = listOf(
        CalcField("hourly", R.string.calc_overtime_field_hour, FieldType.Money),
        CalcField("pct", R.string.calc_overtime_field_pct, FieldType.Percent),
        CalcField("count", R.string.calc_overtime_field_count, FieldType.Number),
    )

    override fun calculate(values: Map<String, Double>): CalcOutcome {
        val hourly = values["hourly"] ?: return CalcOutcome.Error(R.string.error_required)
        val pct = values["pct"] ?: return CalcOutcome.Error(R.string.error_required)
        val count = values["count"] ?: return CalcOutcome.Error(R.string.error_required)
        if (!Validation.requirePositive(hourly)) return CalcOutcome.Error(R.string.error_invalid_number)
        if (!Validation.requirePositive(pct)) return CalcOutcome.Error(R.string.error_invalid_number)
        if (!Validation.requirePositive(count)) return CalcOutcome.Error(R.string.error_invalid_number)

        val total = Labor.overtimeValue(hourly, pct, count)
        return CalcOutcome.Success(
            titleRes = R.string.calc_overtime_value,
            rows = listOf(ResultRow(R.string.calc_overtime_value, Format.money(total))),
        )
    }
}