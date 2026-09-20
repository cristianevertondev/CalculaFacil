package com.cristian.calculafacil.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.Work
import androidx.compose.ui.graphics.vector.ImageVector
import com.cristian.calculafacil.R

/** Agrupamento de calculadoras na Home. */
enum class Category(
    @get:StringRes val titleRes: Int,
    val icon: ImageVector,
) {
    Finance(R.string.category_finance, Icons.Filled.MonetizationOn),
    Labor(R.string.category_labor, Icons.Filled.Work),
    Health(R.string.category_health, Icons.Filled.FitnessCenter),
    Vehicle(R.string.category_vehicle, Icons.Filled.AccountBalanceWallet),
    Converter(R.string.category_converter, Icons.Filled.SwapHoriz),
    Education(R.string.category_education, Icons.Filled.School),
}