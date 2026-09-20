package com.cristian.calculafacil.calculation

/**
 * Cálculos de veículo e cotidiano — lógica pura e testável.
 */
object Vehicle {

    /** Combustível necessário (litros) para uma distância com certo consumo. */
    fun litersNeeded(distanceKm: Double, consumptionKmPerL: Double): Double =
        distanceKm / consumptionKmPerL

    /** Custo da viagem (litros × preço por litro). */
    fun tripCost(distanceKm: Double, consumptionKmPerL: Double, pricePerLiter: Double): Double =
        litersNeeded(distanceKm, consumptionKmPerL) * pricePerLiter

    /** Consumo médio (km/l) dado a distância e os litros abastecidos. */
    fun consumptionKmPerLiter(km: Double, liters: Double): Double = km / liters
}

/** Divisão de conta — lógica pura e testável. */
object Split {

    /** Total com gorjeta ([tipPercent]% sobre [total]). */
    fun totalWithTip(total: Double, tipPercent: Double): Double =
        total * (1.0 + tipPercent / 100.0)

    /** Valor por pessoa após aplicar a gorjeta. */
    fun perPerson(total: Double, tipPercent: Double, people: Double): Double =
        totalWithTip(total, tipPercent) / people
}