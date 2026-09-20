package com.cristian.calculafacil.model.engine

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import com.cristian.calculafacil.R
import com.cristian.calculafacil.calculation.Format
import com.cristian.calculafacil.calculation.Percentage
import com.cristian.calculafacil.calculation.Validation
import com.cristian.calculafacil.model.CalcField
import com.cristian.calculafacil.model.CalcOutcome
import com.cristian.calculafacil.model.CalculatorEngine
import com.cristian.calculafacil.model.Category
import com.cristian.calculafacil.model.FieldType
import com.cristian.calculafacil.model.ResultRow

object DiscountEngine : CalculatorEngine {
    override val id = "discount"
    override val titleRes = R.string.calc_discount_title
    override val descriptionRes = R.string.calc_discount_desc
    override val icon = Icons.Filled.ShoppingCart
    override val category = Category.Finance

    override val fields = listOf(
        CalcField("price", R.string.calc_discount_field_price, FieldType.Money),
        CalcField("pct", R.string.calc_discount_field_pct, FieldType.Percent),
    )

    override fun calculate(values: Map<String, Double>): CalcOutcome {
        val price = values["price"] ?: return CalcOutcome.Error(R.string.error_required)
        val pct = values["pct"] ?: return CalcOutcome.Error(R.string.error_required)
        if (!Validation.requirePositive(price)) return CalcOutcome.Error(R.string.error_invalid_number)
        if (!Validation.requirePositive(pct)) return CalcOutcome.Error(R.string.error_invalid_number)
        if (pct > 100) return CalcOutcome.Error(R.string.error_invalid_number)

        val amount = Percentage.discountAmount(price, pct)
        val final = Percentage.applyDiscount(price, pct)
        return CalcOutcome.Success(
            rows = listOf(
                ResultRow(R.string.calc_discount_final, Format.money(final)),
                ResultRow(R.string.calc_discount_amount, Format.money(amount)),
            ),
        )
    }
}