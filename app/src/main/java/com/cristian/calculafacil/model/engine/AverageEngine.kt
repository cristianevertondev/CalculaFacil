package com.cristian.calculafacil.model.engine

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.School
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

object AverageEngine : CalculatorEngine {
    override val id = "average"
    override val titleRes = R.string.calc_average_title
    override val descriptionRes = R.string.calc_average_desc
    override val icon = Icons.Filled.School
    override val category = Category.Education

    override val fields = listOf(
        CalcField("g1", R.string.calc_average_field_grade1, FieldType.Number),
        CalcField("g2", R.string.calc_average_field_grade2, FieldType.Number),
        CalcField("g3", R.string.calc_average_field_grade3, FieldType.Number),
        CalcField("g4", R.string.calc_average_field_grade4, FieldType.Number),
    )

    override fun calculate(values: Map<String, Double>): CalcOutcome {
        val grades = listOf(values["g1"], values["g2"], values["g3"], values["g4"])
        if (grades.any { it == null }) return CalcOutcome.Error(R.string.error_required)
        if (grades.any { !Validation.requireFinite(it!!) }) return CalcOutcome.Error(R.string.error_invalid_number)
        if (grades.any { it!! < 0.0 || it > 10.0 }) return CalcOutcome.Error(R.string.error_invalid_number)

        val avg = Education.average(grades.map { it!! })
        return CalcOutcome.Success(
            titleRes = R.string.calc_average_result,
            rows = listOf(ResultRow(R.string.calc_average_result, Format.number(avg, 2))),
        )
    }
}