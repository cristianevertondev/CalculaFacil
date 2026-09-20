package com.cristian.calculafacil.model.engine

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RequestQuote
import com.cristian.calculafacil.R
import com.cristian.calculafacil.calculation.Format
import com.cristian.calculafacil.calculation.Interest
import com.cristian.calculafacil.calculation.Validation
import com.cristian.calculafacil.model.CalcField
import com.cristian.calculafacil.model.CalcOutcome
import com.cristian.calculafacil.model.CalculatorEngine
import com.cristian.calculafacil.model.Category
import com.cristian.calculafacil.model.FieldType
import com.cristian.calculafacil.model.ResultRow

object InstallmentEngine : CalculatorEngine {
    override val id = "installment"
    override val titleRes = R.string.calc_installment_title
    override val descriptionRes = R.string.calc_installment_desc
    override val icon = Icons.Filled.RequestQuote
    override val category = Category.Finance

    override val fields = listOf(
        CalcField("amount", R.string.calc_installment_field_amount, FieldType.Money),
        CalcField("rate", R.string.calc_installment_field_rate, FieldType.Percent),
        CalcField("count", R.string.calc_installment_field_count, FieldType.Integer),
    )

    override fun calculate(values: Map<String, Double>): CalcOutcome {
        val amount = values["amount"] ?: return CalcOutcome.Error(R.string.error_required)
        val rate = values["rate"] ?: return CalcOutcome.Error(R.string.error_required)
        val count = values["count"] ?: return CalcOutcome.Error(R.string.error_required)
        if (!Validation.requirePositive(amount)) return CalcOutcome.Error(R.string.error_invalid_number)
        if (!Validation.requirePositive(rate)) return CalcOutcome.Error(R.string.error_invalid_number)
        if (!Validation.requireStrictlyPositive(count)) return CalcOutcome.Error(R.string.error_invalid_number)

        val installment = Interest.priceInstallment(amount, rate, count)
        val total = installment * count
        val interest = total - amount
        if (!total.isFinite()) return CalcOutcome.Error(R.string.error_number_too_large)
        return CalcOutcome.Success(
            rows = listOf(
                ResultRow(R.string.calc_installment_value, Format.money(installment)),
                ResultRow(R.string.calc_installment_total, Format.money(total)),
                ResultRow(R.string.calc_installment_interest, Format.money(interest)),
            ),
        )
    }
}