package com.cristian.calculafacil.model.engine

import com.cristian.calculafacil.model.CalcOutcome
import org.junit.Assert.assertTrue
import org.junit.Test

private fun CalcOutcome.isError(): Boolean = this is CalcOutcome.Error
private fun input(vararg pairs: Pair<String, Double>) = mapOf(*pairs)

class ImcEngineTest {
    @Test
    fun `normal bmi result`() {
        val o = ImcEngine.calculate(input("weight" to 70.0, "height" to 1.75))
        val v = (o as CalcOutcome.Success).rows
        assertTrue(v.any { it.labelRes == com.cristian.calculafacil.R.string.calc_imc_value })
        assertTrue(v.any { it.valueRes == com.cristian.calculafacil.R.string.imc_normal })
    }

    @Test
    fun `zero weight error`() {
        assertTrue(ImcEngine.calculate(input("weight" to 0.0, "height" to 1.75)).isError())
    }

    @Test
    fun `zero height error`() {
        assertTrue(ImcEngine.calculate(input("weight" to 70.0, "height" to 0.0)).isError())
    }
}

class CaloriesEngineTest {
    @Test
    fun `male bmr`() {
        val o = CaloriesEngine.calculate(input("weight" to 70.0, "height" to 175.0, "age" to 30.0, "sex" to 1.0))
        val v = (o as CalcOutcome.Success).rows
        assertTrue(v.any { it.value == "1.649" })
        assertTrue(v.any { it.suffixRes == com.cristian.calculafacil.R.string.suffix_kcal })
    }

    @Test
    fun `missing sex error`() {
        assertTrue(
            CaloriesEngine.calculate(input("weight" to 70.0, "height" to 175.0, "age" to 30.0)).isError(),
        )
    }
}