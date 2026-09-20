package com.cristian.calculafacil.ui.screens.converter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cristian.calculafacil.R
import com.cristian.calculafacil.calculation.Format
import com.cristian.calculafacil.model.CalcOutcome
import com.cristian.calculafacil.model.Converter
import com.cristian.calculafacil.model.ConverterDimension
import com.cristian.calculafacil.model.ResultRow
import com.cristian.calculafacil.ui.components.CalculatorInputField
import com.cristian.calculafacil.ui.components.ConverterOptionDropdown
import com.cristian.calculafacil.ui.components.ResultCard
import com.cristian.calculafacil.viewmodel.ConverterError
import com.cristian.calculafacil.viewmodel.ConverterViewModel

/** Tela de conversor (unidades ou moedas). */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConverterScreen(
    converter: Converter,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val vm: ConverterViewModel = viewModel(factory = ConverterViewModel.Factory(converter.id))

    val outcome: CalcOutcome? = buildOutcome(vm)

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(stringResource(converter.titleRes)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.action_back),
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                ),
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter)
                .widthIn(max = 640.dp)
                .verticalScroll(rememberScrollState())
                .imePadding()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = stringResource(converter.descriptionRes),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            if (converter.dimensions.isNotEmpty()) {
                DimensionRow(
                    dimensions = converter.dimensions,
                    selectedId = vm.selectedDimensionId(),
                    onSelect = vm::onDimensionSelect,
                )
            }

            CalculatorInputField(
                value = vm.value,
                onValueChange = vm::onValueChange,
                label = stringResource(R.string.converter_value),
                fieldType = com.cristian.calculafacil.model.FieldType.Number,
                modifier = Modifier.fillMaxWidth(),
            )

            FromToRow(vm = vm)

            Button(
                onClick = vm::convert,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(stringResource(R.string.action_calculate))
            }

            OutlinedButton(
                onClick = vm::clear,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(stringResource(R.string.action_clear))
            }

            ResultCard(outcome = outcome, modifier = Modifier.fillMaxWidth())

            if (converter.id == "currency") {
                ReferenceRateNotice()
            }
        }
        }
    }
}

@Composable
private fun DimensionRow(
    dimensions: List<ConverterDimension>,
    selectedId: String?,
    onSelect: (String?) -> Unit,
) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(dimensions, key = { it.id }) { dimension ->
            FilterChip(
                selected = selectedId == dimension.id,
                onClick = { onSelect(dimension.id) },
                label = { Text(stringResource(dimension.labelRes)) },
            )
        }
    }
}

@Composable
private fun FromToRow(vm: ConverterViewModel) {
    val options = vm.options()
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        ConverterOptionDropdown(
            label = stringResource(R.string.converter_from),
            options = options,
            selectedId = vm.fromId,
            onSelect = vm::onFromSelect,
            modifier = Modifier.weight(1f),
        )
        IconButton(onClick = vm::swap, modifier = Modifier.size(48.dp)) {
            Icon(Icons.Filled.SwapVert, contentDescription = stringResource(R.string.converter_swap))
        }
        ConverterOptionDropdown(
            label = stringResource(R.string.converter_to),
            options = options,
            selectedId = vm.toId,
            onSelect = vm::onToSelect,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun buildOutcome(vm: ConverterViewModel): CalcOutcome? {
    val result by vm.result
    val error by vm.error
    val toRes = vm.selectedToLabelRes()
    return when (error) {
        ConverterError.INVALID_INPUT -> CalcOutcome.Error(R.string.error_invalid_number)
        ConverterError.CONVERSION -> CalcOutcome.Error(R.string.error_conversion)
        null -> {
            if (result != null) {
                val suffix = toRes?.let { stringResource(it) }.orEmpty()
                CalcOutcome.Success(
                    titleRes = R.string.converter_result,
                    rows = listOf(
                        ResultRow(R.string.converter_result, "${Format.number(result!!, 4)} $suffix"),
                    ),
                )
            } else {
                null
            }
        }
    }
}

/** Aviso destacado de que as taxas de câmbio são de referência, não em tempo real. */
@Composable
private fun ReferenceRateNotice() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Icon(
                imageVector = Icons.Filled.Warning,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSecondaryContainer,
            )
            Text(
                text = stringResource(R.string.converter_rate_note),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
            )
        }
    }
}