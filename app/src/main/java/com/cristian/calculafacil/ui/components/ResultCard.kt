package com.cristian.calculafacil.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.cristian.calculafacil.model.CalcOutcome

/**
 * Exibe o resultado de um cálculo. A primeira linha de um sucesso é o
 * destaque principal. Erros aparecem com estilo próprio.
 */
@Composable
fun ResultCard(
    outcome: CalcOutcome?,
    modifier: Modifier = Modifier,
) {
    AnimatedVisibility(
        visible = outcome != null,
        enter = fadeIn(tween(250)) + slideInVertically(tween(250)) { it / 4 },
        modifier = modifier,
    ) {
        when (val o = outcome) {
            is CalcOutcome.Success -> SuccessCard(o)
            is CalcOutcome.Error -> ErrorCard(o)
            null -> {}
        }
    }
}

@Composable
private fun SuccessCard(outcome: CalcOutcome.Success) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            outcome.titleRes?.let {
                Text(
                    text = stringResource(it),
                    style = MaterialTheme.typography.titleMedium,
                )
            }
            outcome.rows.forEachIndexed { index, row ->
                if (index > 0) {
                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.15f),
                    )
                }
                ResultRowRow(row = row, highlighted = index == 0)
            }
        }
    }
}

@Composable
private fun ResultRowRow(
    row: com.cristian.calculafacil.model.ResultRow,
    highlighted: Boolean,
) {
    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Text(
            text = stringResource(row.labelRes),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f),
        )
        val suffix = row.suffixRes?.let { stringResource(it) }.orEmpty()
        val display = if (row.valueRes != null) {
            stringResource(row.valueRes)
        } else if (suffix.isNotEmpty()) {
            "${row.value} $suffix"
        } else {
            row.value
        }
        Text(
            text = display,
            style = if (highlighted) {
                MaterialTheme.typography.headlineMedium
            } else {
                MaterialTheme.typography.titleLarge
            },
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )
    }
}

@Composable
private fun ErrorCard(outcome: CalcOutcome.Error) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer,
            contentColor = MaterialTheme.colorScheme.onErrorContainer,
        ),
    ) {
        Text(
            text = stringResource(outcome.messageRes),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(20.dp),
        )
    }
}