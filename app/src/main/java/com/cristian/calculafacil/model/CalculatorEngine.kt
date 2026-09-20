package com.cristian.calculafacil.model

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

/** Uma linha de resultado apresentada após o cálculo. */
data class ResultRow(
    @get:StringRes val labelRes: Int,
    val value: String = "",
    /** Quando presente, exibe stringResource([valueRes]) em vez de [value]. */
    @get:StringRes val valueRes: Int? = null,
    /** Sufixo localizado exibido após [value] (ex.: "anos", "km/l"). */
    @get:StringRes val suffixRes: Int? = null,
)

/** Desfecho de um cálculo: sucesso com linhas de resultado ou erro localizado. */
sealed interface CalcOutcome {
    /** Resultado com linhas de valor. O primeiro `row` é o destaque principal. */
    data class Success(
        @StringRes val titleRes: Int? = null,
        val rows: List<ResultRow>,
    ) : CalcOutcome

    data class Error(
        @StringRes val messageRes: Int,
    ) : CalcOutcome
}

/**
 * Contrato de uma calculadora. A UI é genérica e dirigida por dados:
 * [fields] define os campos de entrada e [calculate] produz o resultado.
 *
 * A lógica matemática de [calculate] deve delegar para funções puras em
 * `calculation` (testáveis em JVM). A UI nunca implementa matemática.
 */
interface CalculatorEngine : ToolItem {
    override val id: String
    @get:StringRes override val titleRes: Int
    @get:StringRes override val descriptionRes: Int
    override val icon: ImageVector
    override val category: Category
    val fields: List<CalcField>

    fun calculate(values: Map<String, Double>): CalcOutcome
}

/** Um campo de entrada de uma calculadora. */
data class CalcField(
    val key: String,
    @get:StringRes val labelRes: Int,
    val type: FieldType,
    @get:StringRes val suffixRes: Int? = null,
    val placeholder: String = "",
    /** Para [FieldType.Toggle]: rótulos das opções (0 e 1). */
    val toggleLabels: Pair<Int, Int>? = null,
)