package com.cristian.calculafacil.model.engine

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MonitorWeight
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

object ImcEngine : CalculatorEngine {
    override val id = "imc"
    override val titleRes = R.string.calc_imc_title
    override val descriptionRes = R.string.calc_imc_desc
    override val icon = Icons.Filled.MonitorWeight
    override val category = Category.Health

    override val fields = listOf(
        CalcField("weight", R.string.calc_imc_field_weight, FieldType.Number),
        CalcField("height", R.string.calc_imc_field_height, FieldType.Number),
    )

    override fun calculate(values: Map<String, Double>): CalcOutcome {
        val weight = values["weight"] ?: return CalcOutcome.Error(R.string.error_required)
        val height = values["height"] ?: return CalcOutcome.Error(R.string.error_required)
        if (!Validation.requireStrictlyPositive(weight)) return CalcOutcome.Error(R.string.error_invalid_number)
        if (!Validation.requireStrictlyPositive(height)) return CalcOutcome.Error(R.string.error_invalid_number)
        if (height > 3.0) return CalcOutcome.Error(R.string.error_invalid_number)

        val bmi = Health.bmi(weight, height)
        val categoryRes = when (Health.bmiCategory(bmi)) {
            Health.BmiCategory.UNDERWEIGHT -> R.string.imc_underweight
            Health.BmiCategory.NORMAL -> R.string.imc_normal
            Health.BmiCategory.OVERWEIGHT -> R.string.imc_overweight
            Health.BmiCategory.OBESITY -> R.string.imc_obesity
        }
        return CalcOutcome.Success(
            rows = listOf(
                ResultRow(R.string.calc_imc_value, Format.number(bmi, 1)),
                ResultRow(R.string.calc_imc_category, valueRes = categoryRes),
            ),
        )
    }
}