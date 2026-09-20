package com.cristian.calculafacil.model.engine

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import com.cristian.calculafacil.R
import com.cristian.calculafacil.calculation.Format
import com.cristian.calculafacil.calculation.Health
import com.cristian.calculafacil.calculation.Validation
import com.cristian.calculafacil.model.CalcField
import com.cristian.calculafacil.model.CalcOutcome
import com.cristian.calculafacil.model.CalculatorEngine
import com.cristian.calculafacil.model.Category
import com.cristian.calculafacil.model.FieldType
import com.cristian.calculafacil.model.ResultRow

object CaloriesEngine : CalculatorEngine {
    override val id = "calories"
    override val titleRes = R.string.calc_calories_title
    override val descriptionRes = R.string.calc_calories_desc
    override val icon = Icons.Filled.LocalFireDepartment
    override val category = Category.Health

    override val fields = listOf(
        CalcField("weight", R.string.calc_calories_field_weight, FieldType.Number),
        CalcField("height", R.string.calc_calories_field_height, FieldType.Number),
        CalcField("age", R.string.calc_calories_field_age, FieldType.Integer),
        CalcField(
            "sex",
            R.string.calc_calories_field_sex,
            FieldType.Toggle,
            toggleLabels = R.string.sex_female to R.string.sex_male,
        ),
    )

    override fun calculate(values: Map<String, Double>): CalcOutcome {
        val weight = values["weight"] ?: return CalcOutcome.Error(R.string.error_required)
        val height = values["height"] ?: return CalcOutcome.Error(R.string.error_required)
        val age = values["age"] ?: return CalcOutcome.Error(R.string.error_required)
        val sex = values["sex"] ?: return CalcOutcome.Error(R.string.error_required)
        if (!Validation.requireStrictlyPositive(weight)) return CalcOutcome.Error(R.string.error_invalid_number)
        if (!Validation.requireStrictlyPositive(height)) return CalcOutcome.Error(R.string.error_invalid_number)
        if (!Validation.requireStrictlyPositive(age)) return CalcOutcome.Error(R.string.error_invalid_number)
        if (sex != 0.0 && sex != 1.0) return CalcOutcome.Error(R.string.error_invalid_number)

        val bmr = Health.basalMetabolism(weight, height, age, sex)
        return CalcOutcome.Success(
            titleRes = R.string.calc_calories_bmr,
            rows = listOf(
                ResultRow(R.string.calc_calories_bmr, Format.integer(bmr), suffixRes = R.string.suffix_kcal),
            ),
        )
    }
}