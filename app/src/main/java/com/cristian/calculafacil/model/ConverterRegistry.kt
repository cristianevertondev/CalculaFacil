package com.cristian.calculafacil.model

/** Registro central dos conversores (unidades e moedas). */
object ConverterRegistry {
    val all: List<Converter> = listOf(UnitsConverter, CurrencyConverterTool)

    fun byId(id: String): Converter? = all.firstOrNull { it.id == id }

    /** Todos os itens de ferramenta (calculadoras + conversores) para a Home. */
    val allTools: List<ToolItem> = CalculatorCatalog.all + all
}