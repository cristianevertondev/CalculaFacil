package com.cristian.calculafacil.model.engine

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Functions
import com.cristian.calculafacil.R
import com.cristian.calculafacil.calculation.Format
import com.cristian.calculafacil.calculation.Proportion
import com.cristian.calculafacil.calculation.Validation
import com.cristian.calculafacil.model.CalcField
import com.cristian.calculafacil.model.CalcOutcome
import com.cristian.calculafacil.model.CalculatorEngine
import com.cristian.calculafacil.model.Category
import com.cristian.calculafacil.model.FieldType
import com.cristian.calculafacil.model.ResultRow

object Rule3Engine : CalculatorEngine {
    override val id = "rule3"
    override val titleRes = R.string.calc_rule3_title
    override val descriptionRes = R.string.calc_rule3_desc
    override val icon = Icons.Filled.Functions
    override val category = Category.Finance

    override val fields = listOf(
        CalcField("a", R.string.calc_rule3_a, FieldType.Number),
        CalcField("b", R.string.calc_rule3_b, FieldType.Number),
        CalcField("c", R.string.calc_rule3_c, FieldType.Number),
    )

    override fun calculate(values: Map<String, Double>): CalcOutcome {
        val a = values["a"] ?: return CalcOutcome.Error(R.string.error_required)
        val b = values["b"] ?: return CalcOutcome.Error(R.string.error_required)
        val c = values["c"] ?: return CalcOutcome.Error(R.string.error_required)
        if (!Validation.requireFinite(a) || !Validation.requireFinite(b) || !Validation.requireFinite(c)) {
            return CalcOutcome.Error(R.string.error_invalid_number)
        }
        if (a == 0.0) return CalcOutcome.Error(R.string.error_zero_divisor)

        val x = Proportion.ruleOfThree(a, b, c)
        return CalcOutcome.Success(
            titleRes = R.string.calc_rule3_x,
            rows = listOf(ResultRow(R.string.calc_rule3_x, Format.number(x, 4))),
        )
    }
}