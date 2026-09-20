package com.cristian.calculafacil.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cristian.calculafacil.R
import com.cristian.calculafacil.calculation.Parsing
import com.cristian.calculafacil.model.CalcOutcome
import com.cristian.calculafacil.model.CalculatorEngine
import com.cristian.calculafacil.model.CalculatorCatalog
import com.cristian.calculafacil.model.FieldType

/**
 * Estado da tela de uma calculadora. Mantém os valores de entrada e o
 * resultado (sobrevive à rotação). É dirigido por dados: conhece a [engine]
 * que define campos e cálculo.
 */
class CalculatorViewModel(private val engine: CalculatorEngine) : ViewModel() {

    val id: String get() = engine.id
    val fields get() = engine.fields

    private val _inputs = mutableStateMapOf<String, String>()
    val inputs: Map<String, String> get() = _inputs

    private val _outcome = mutableStateOf<CalcOutcome?>(null)
    val outcome: State<CalcOutcome?> get() = _outcome

    fun onValueChange(key: String, value: String) {
        _inputs[key] = value
    }

    fun calculate() {
        val parsed = mutableMapOf<String, Double>()
        for (field in engine.fields) {
            val raw = _inputs[field.key].orEmpty()
            val value = Parsing.parseDecimal(raw)
            if (value == null) {
                _outcome.value = CalcOutcome.Error(R.string.error_invalid_number)
                return
            }
            if (field.type == FieldType.Integer && !Parsing.isWhole(value)) {
                _outcome.value = CalcOutcome.Error(R.string.error_invalid_number)
                return
            }
            parsed[field.key] = value
        }
        _outcome.value = engine.calculate(parsed)
    }

    fun clear() {
        _inputs.clear()
        _outcome.value = null
    }

    class Factory(private val engineId: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val engine = requireNotNull(CalculatorCatalog.byId(engineId)) {
                "Calculadora desconhecida: $engineId"
            }
            @Suppress("UNCHECKED_CAST")
            return CalculatorViewModel(engine) as T
        }
    }
}