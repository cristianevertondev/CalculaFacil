package com.cristian.calculafacil.model

import com.cristian.calculafacil.model.engine.AgeEngine
import com.cristian.calculafacil.model.engine.AverageEngine
import com.cristian.calculafacil.model.engine.CaloriesEngine
import com.cristian.calculafacil.model.engine.CompoundInterestEngine
import com.cristian.calculafacil.model.engine.ConsumptionEngine
import com.cristian.calculafacil.model.engine.DiscountEngine
import com.cristian.calculafacil.model.engine.FinancingEngine
import com.cristian.calculafacil.model.engine.FuelEngine
import com.cristian.calculafacil.model.engine.ImcEngine
import com.cristian.calculafacil.model.engine.IncreaseEngine
import com.cristian.calculafacil.model.engine.InstallmentEngine
import com.cristian.calculafacil.model.engine.NeededGradeEngine
import com.cristian.calculafacil.model.engine.OvertimeEngine
import com.cristian.calculafacil.model.engine.PercentEngine
import com.cristian.calculafacil.model.engine.Rule3Engine
import com.cristian.calculafacil.model.engine.SalaryEngine
import com.cristian.calculafacil.model.engine.SimpleInterestEngine
import com.cristian.calculafacil.model.engine.SplitEngine
import com.cristian.calculafacil.model.engine.TerminationEngine
import com.cristian.calculafacil.model.engine.ThirteenthEngine
import com.cristian.calculafacil.model.engine.VacationEngine

/**
 * Registro central de todas as calculadoras disponíveis.
 * As fases seguintes adicionam novos engines aqui.
 */
object CalculatorCatalog {
    val all: List<CalculatorEngine> = listOf(
        PercentEngine,
        DiscountEngine,
        IncreaseEngine,
        Rule3Engine,
        SimpleInterestEngine,
        CompoundInterestEngine,
        InstallmentEngine,
        FinancingEngine,
        SalaryEngine,
        OvertimeEngine,
        VacationEngine,
        ThirteenthEngine,
        TerminationEngine,
        ImcEngine,
        CaloriesEngine,
        FuelEngine,
        ConsumptionEngine,
        SplitEngine,
        AverageEngine,
        NeededGradeEngine,
        AgeEngine,
    )

    fun byId(id: String): CalculatorEngine? = all.firstOrNull { it.id == id }
}