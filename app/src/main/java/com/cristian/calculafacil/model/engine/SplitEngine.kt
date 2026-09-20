package com.cristian.calculafacil.model.engine

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import com.cristian.calculafacil.R
import com.cristian.calculafacil.calculation.Format
import com.cristian.calculafacil.calculation.Split
import com.cristian.calculafacil.calculation.Validation
import com.cristian.calculafacil.model.CalcField
import com.cristian.calculafacil.model.CalcOutcome
import com.cristian.calculafacil.model.CalculatorEngine
import com.cristian.calculafacil.model.Category
import com.cristian.calculafacil.model.FieldType
import com.cristian.calculafacil.model.ResultRow

object SplitEngine : CalculatorEngine {
    override val id = "split"
    override val titleRes = R.string.calc_split_title
    override val descriptionRes = R.string.calc_split_desc
    override val icon = Icons.Filled.Group
    override val category = Category.Vehicle

    override val fields = listOf(
        CalcField("total", R.string.calc_split_field_total, FieldType.Money),
        CalcField("people", R.string.calc_split_field_people, FieldType.Integer),
        CalcField("tip", R.string.calc_split_field_gorjeta, FieldType.Percent),
    )

    override fun calculate(values: Map<String, Double>): CalcOutcome {
        val total = values["total"] ?: return CalcOutcome.Error(R.string.error_required)
        val people = values["people"] ?: return CalcOutcome.Error(R.string.error_required)
        val tip = values["tip"] ?: return CalcOutcome.Error(R.string.error_required)
        if (!Validation.requirePositive(total)) return CalcOutcome.Error(R.string.error_invalid_number)
        if (!Validation.requireStrictlyPositive(people)) return CalcOutcome.Error(R.string.error_invalid_number)
        if (!Validation.requirePositive(tip)) return CalcOutcome.Error(R.string.error_invalid_number)

        val totalWithTip = Split.totalWithTip(total, tip)
        val perPerson = Split.perPerson(total, tip, people)
        return CalcOutcome.Success(
            rows = listOf(
                ResultRow(R.string.calc_split_per_person, Format.money(perPerson)),
                ResultRow(R.string.calc_split_total_with, Format.money(totalWithTip)),
            ),
        )
    }
}