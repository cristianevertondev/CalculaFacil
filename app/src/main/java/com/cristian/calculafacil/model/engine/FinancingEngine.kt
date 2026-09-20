package com.cristian.calculafacil.model.engine

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
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

object FinancingEngine : CalculatorEngine {
    override val id = "financing"
    override val titleRes = R.string.calc_finance_title
    override val descriptionRes = R.string.calc_finance_desc
    override val icon = Icons.Filled.AccountBalance
    override val category = Category.Finance

    override val fields = listOf(
        CalcField("value", R.string.calc_finance_field_value, FieldType.Money),
        CalcField("rate", R.string.calc_finance_field_rate, FieldType.Percent),
        CalcField("months", R.string.calc_finance_field_months, FieldType.Integer),
    )

    override fun calculate(values: Map<String, Double>): CalcOutcome {
        val value = values["value"] ?: return CalcOutcome.Error(R.string.error_required)
        val rate = values["rate"] ?: return CalcOutcome.Error(R.string.error_required)
        val months = values["months"] ?: return CalcOutcome.Error(R.string.error_required)
        if (!Validation.requirePositive(value)) return CalcOutcome.Error(R.string.error_invalid_number)
        if (!Validation.requirePositive(rate)) return CalcOutcome.Error(R.string.error_invalid_number)
        if (!Validation.requireStrictlyPositive(months)) return CalcOutcome.Error(R.string.error_invalid_number)

        val price = Interest.priceInstallment(value, rate, months)
        val sacFirst = Interest.sacFirstInstallment(value, rate, months)
        val sacLast = Interest.sacLastInstallment(value, rate, months)
        val priceTotal = Interest.priceTotal(value, rate, months)
        val sacTotal = Interest.sacTotal(value, rate, months)
        if (!priceTotal.isFinite() || !sacTotal.isFinite()) return CalcOutcome.Error(R.string.error_number_too_large)
        return CalcOutcome.Success(
            rows = listOf(
                ResultRow(R.string.calc_finance_price_parcel, Format.money(price)),
                ResultRow(R.string.calc_finance_sac_first, Format.money(sacFirst)),
                ResultRow(R.string.calc_finance_sac_last, Format.money(sacLast)),
                ResultRow(R.string.calc_finance_total_price, Format.money(priceTotal)),
                ResultRow(R.string.calc_finance_total_sac, Format.money(sacTotal)),
            ),
        )
    }
}