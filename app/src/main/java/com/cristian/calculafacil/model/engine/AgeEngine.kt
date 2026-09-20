package com.cristian.calculafacil.model.engine

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cake
import com.cristian.calculafacil.R
import com.cristian.calculafacil.calculation.AgeCalculator
import com.cristian.calculafacil.calculation.Format
import com.cristian.calculafacil.calculation.Validation
import com.cristian.calculafacil.model.CalcField
import com.cristian.calculafacil.model.CalcOutcome
import com.cristian.calculafacil.model.CalculatorEngine
import com.cristian.calculafacil.model.Category
import com.cristian.calculafacil.model.FieldType
import com.cristian.calculafacil.model.ResultRow
import java.time.LocalDate

object AgeEngine : CalculatorEngine {
    override val id = "age"
    override val titleRes = R.string.calc_age_title
    override val descriptionRes = R.string.calc_age_desc
    override val icon = Icons.Filled.Cake
    override val category = Category.Education

    override val fields = listOf(
        CalcField("day", R.string.calc_age_field_day, FieldType.Integer),
        CalcField("month", R.string.calc_age_field_month, FieldType.Integer),
        CalcField("year", R.string.calc_age_field_year, FieldType.Integer),
    )

    override fun calculate(values: Map<String, Double>): CalcOutcome {
        val day = values["day"] ?: return CalcOutcome.Error(R.string.error_required)
        val month = values["month"] ?: return CalcOutcome.Error(R.string.error_required)
        val year = values["year"] ?: return CalcOutcome.Error(R.string.error_required)
        if (!Validation.requireFinite(day) || !Validation.requireFinite(month) || !Validation.requireFinite(year)) {
            return CalcOutcome.Error(R.string.error_invalid_number)
        }
        if (year < 1900.0) return CalcOutcome.Error(R.string.error_invalid_number)

        val birthDate = AgeCalculator.birthDate(day.toInt(), month.toInt(), year.toInt())
            ?: return CalcOutcome.Error(R.string.error_invalid_date)

        val today = LocalDate.now()
        if (birthDate.isAfter(today)) return CalcOutcome.Error(R.string.error_future_date)

        val age = AgeCalculator.age(day.toInt(), month.toInt(), year.toInt(), today)
        return CalcOutcome.Success(
            titleRes = R.string.calc_age_result,
            rows = listOf(
                ResultRow(R.string.calc_age_result, Format.integer(age.toDouble()), suffixRes = R.string.suffix_years),
            ),
        )
    }
}