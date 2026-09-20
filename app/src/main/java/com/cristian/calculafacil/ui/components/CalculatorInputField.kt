package com.cristian.calculafacil.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.FilterChip
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.cristian.calculafacil.R
import com.cristian.calculafacil.model.FieldType

/** Campo de entrada padrão das calculadoras. */
@Composable
fun CalculatorInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    fieldType: FieldType,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    supportingText: String? = null,
    toggleLabels: Pair<Int, Int>? = null,
) {
    when (fieldType) {
        FieldType.Toggle -> ToggleField(
            value = value,
            onValueChange = onValueChange,
            label = label,
            modifier = modifier,
            toggleLabels = toggleLabels,
        )
        else -> OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType(fieldType)),
            prefix = {
                if (fieldType == FieldType.Money) {
                    Text(stringResource(R.string.currency_symbol))
                }
            },
            suffix = {
                if (fieldType == FieldType.Percent) {
                    Text(stringResource(R.string.percent_symbol))
                }
            },
            isError = isError,
            supportingText = supportingText?.let { { Text(it) } },
            modifier = modifier,
        )
    }
}

@Composable
private fun ToggleField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    toggleLabels: Pair<Int, Int>? = null,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Text(label, style = androidx.compose.material3.MaterialTheme.typography.labelLarge)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            toggleLabels?.let { labels ->
                FilterChip(
                    selected = value == "0",
                    onClick = { onValueChange("0") },
                    label = { Text(stringResource(labels.first)) },
                )
                FilterChip(
                    selected = value == "1",
                    onClick = { onValueChange("1") },
                    label = { Text(stringResource(labels.second)) },
                )
            }
        }
    }
}

private fun keyboardType(fieldType: FieldType): KeyboardType = when (fieldType) {
    FieldType.Money, FieldType.Number, FieldType.Percent -> KeyboardType.Decimal
    FieldType.Integer, FieldType.Toggle -> KeyboardType.Number
}