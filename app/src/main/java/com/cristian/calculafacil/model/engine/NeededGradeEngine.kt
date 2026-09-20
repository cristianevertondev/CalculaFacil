package com.cristian.calculafacil.model.engine

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Grade
import com.cristian.calculafacil.R
import com.cristian.calculafacil.calculation.Education
import com.cristian.calculafacil.calculation.Format
import com.cristian.calculafacil.calculation.Validation
import com.cristian.calculafacil.model.CalcField
import com.cristian.calculafacil.model.CalcOutcome
import com.cristian.calculafacil.model.CalculatorEngine
import com.cristian.calculafacil.model.Category
import com.cristian.calculafacil.model.FieldType
import com.cristian.calculafacil.model.ResultRow

object NeededGradeEngine : CalculatorEngine {
    override val id = "needed_grade"
    override val titleRes = R.string.calc_needed_title
    override val descriptionRes = R.string.calc_needed_desc
    override val icon = Icons.Filled.Grade
    override val category = Category.Education

    override val fields = listOf(
        CalcField("current", R.string.calc_needed_field_current, FieldType.Number),
        CalcField("passing", R.string.calc_needed_field_passing, FieldType.Number),
    )

    override fun calculate(values: Map<String, Double>): CalcOutcome {
        val current = values["current"] ?: return CalcOutcome.Error(R.string.error_required)
        val passing = values["passing"] ?: return CalcOutcome.Error(R.string.error_required)
        if (!Validation.requireFinite(current) || !Validation.requireFinite(passing)) {
            return CalcOutcome.Error(R.string.error_invalid_number)
        }
        if (current < 0.0 || current > 10.0 || passing < 0.0 || passing > 10.0) {
            return CalcOutcome.Error(R.string.error_invalid_number)
        }

        val needed = Education.neededGrade(current, passing)
        return CalcOutcome.Success(
            rows = listOf(
                ResultRow(
                    R.string.calc_needed_result,
                    value = if (needed == 0.0) "" else Format.number(needed, 2),
                    valueRes = if (needed == 0.0) R.string.calc_needed_already else null,
                ),
            ),
        )
    }
}