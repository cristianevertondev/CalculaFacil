package com.cristian.calculafacil.model

/** Define como um campo de entrada deve ser exibido e interpretado. */
enum class FieldType {
    /** Valor monetário (BRL). */
    Money,

    /** Número genérico (aceita decimais). */
    Number,

    /** Percentual — não exibe sufixo de moeda. */
    Percent,

    /** Inteiro (ex.: quantidade, meses). */
    Integer,

    /** Seleção binária (ex.: sexo). Valor interpretado como 0 ou 1. */
    Toggle,
}