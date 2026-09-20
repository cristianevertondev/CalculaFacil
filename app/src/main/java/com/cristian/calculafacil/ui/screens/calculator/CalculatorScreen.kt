package com.cristian.calculafacil.ui.screens.calculator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.cristian.calculafacil.model.CalculatorEngine
import com.cristian.calculafacil.ui.components.CalculatorInputField
import com.cristian.calculafacil.ui.components.ResultCard
import com.cristian.calculafacil.viewmodel.CalculatorViewModel

/** Tela genérica de calculadora, dirigida por dados da [engine]. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorScreen(
    engine: CalculatorEngine,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel: CalculatorViewModel = viewModel(
        factory = CalculatorViewModel.Factory(engine.id),
    )
    val outcome by viewModel.outcome

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(stringResource(engine.titleRes)) },
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
                text = stringResource(engine.descriptionRes),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            for (field in engine.fields) {
                CalculatorInputField(
                    value = viewModel.inputs[field.key].orEmpty(),
                    onValueChange = { viewModel.onValueChange(field.key, it) },
                    label = stringResource(field.labelRes),
                    fieldType = field.type,
                    toggleLabels = field.toggleLabels,
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            Button(
                onClick = viewModel::calculate,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(stringResource(R.string.action_calculate))
            }

            OutlinedButton(
                onClick = viewModel::clear,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(stringResource(R.string.action_clear))
            }

            ResultCard(
                outcome = outcome,
                modifier = Modifier.fillMaxWidth(),
            )

            if (engine.id == "termination") {
                Text(
                    text = stringResource(R.string.calc_termination_note),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
        }
    }
}