package com.cristian.calculafacil.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.cristian.calculafacil.R
import com.cristian.calculafacil.calculation.Parsing
import com.cristian.calculafacil.model.Converter
import com.cristian.calculafacil.model.ConverterOption
import com.cristian.calculafacil.model.ConverterRegistry

/** Erro tipado da tela de conversor. */
enum class ConverterError { INVALID_INPUT, CONVERSION }

/** Estado da tela de conversor (unidades/moedas). Sobrevive à rotação. */
class ConverterViewModel(private val converter: Converter) : ViewModel() {

    var value by mutableStateOf("")
        private set

    private var dimensionId by mutableStateOf<String?>(converter.dimensions.firstOrNull()?.id)
    var fromId by mutableStateOf(converter.initialFromId)
        private set
    var toId by mutableStateOf(converter.initialToId)
        private set

    private val _result = mutableStateOf<Double?>(null)
    val result: State<Double?> get() = _result

    private val _error = mutableStateOf<ConverterError?>(null)
    val error: State<ConverterError?> get() = _error

    val dimensions = converter.dimensions

    fun selectedDimensionId(): String? = dimensionId

    /** Opções "De"/"Para" conforme a dimensão selecionada (ou lista de moedas). */
    fun options(): List<ConverterOption> = converter.optionsForDimension(dimensionId)

    fun onValueChange(newValue: String) {
        value = newValue
    }

    fun onDimensionSelect(id: String?) {
        dimensionId = id
        val opts = converter.optionsForDimension(id)
        // Protege contra dimensão sem opções: evita crash e mantém estado seguro.
        val first = opts.firstOrNull()?.id ?: return
        fromId = opts.firstOrNull { it.id == fromId }?.id ?: first
        toId = opts.firstOrNull { it.id == toId }?.id ?: first
        _result.value = null
        _error.value = null
    }

    fun onFromSelect(id: String) {
        fromId = id
    }

    fun onToSelect(id: String) {
        toId = id
    }

    fun selectedFromLabelRes(): Int? = converter.optionLabelRes(fromId)
    fun selectedToLabelRes(): Int? = converter.optionLabelRes(toId)

    fun convert() {
        val parsed = Parsing.parseDecimal(value)
        if (parsed == null) {
            _error.value = ConverterError.INVALID_INPUT
            _result.value = null
            return
        }
        val out = converter.convert(parsed, fromId, toId)
        if (out == null) {
            _error.value = ConverterError.CONVERSION
            _result.value = null
            return
        }
        _error.value = null
        _result.value = out
    }

    fun swap() {
        val tmp = fromId
        fromId = toId
        toId = tmp
    }

    fun clear() {
        value = ""
        _result.value = null
        _error.value = null
    }

    class Factory(private val converterId: String) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val converter = requireNotNull(ConverterRegistry.byId(converterId)) {
                "Conversor desconhecido: $converterId"
            }
            return ConverterViewModel(converter) as T
        }
    }
}