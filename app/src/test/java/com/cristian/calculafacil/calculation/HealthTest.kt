package com.cristian.calculafacil.calculation

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class HealthTest {

    @Test
    fun `bmi normal`() {
        assertEquals(22.86, Health.bmi(70.0, 1.75), 0.01)
    }

    @Test
    fun `bmi zero height is infinity`() {
        assertTrue(Health.bmi(70.0, 0.0).isInfinite())
    }

    @Test
    fun `bmi underweight`() {
        assertEquals(Health.BmiCategory.UNDERWEIGHT, Health.bmiCategory(17.0))
    }

    @Test
    fun `bmi normal boundary`() {
        assertEquals(Health.BmiCategory.NORMAL, Health.bmiCategory(18.5))
    }

    @Test
    fun `bmi overweight`() {
        assertEquals(Health.BmiCategory.OVERWEIGHT, Health.bmiCategory(27.0))
    }

    @Test
    fun `bmi obesity`() {
        assertEquals(Health.BmiCategory.OBESITY, Health.bmiCategory(31.0))
    }

    @Test
    fun `bmr male`() {
        // Mifflin-St Jeor: 10*70 + 6.25*175 - 5*30 + 5 = 1648.75
        assertEquals(1648.75, Health.basalMetabolism(70.0, 175.0, 30.0, 1.0), 0.01)
    }

    @Test
    fun `bmr female`() {
        // 10*60 + 6.25*165 - 5*30 - 161
        assertEquals(1320.25, Health.basalMetabolism(60.0, 165.0, 30.0, 0.0), 0.01)
    }

    @Test
    fun `bmr sex default treats as female`() {
        assertEquals(
            Health.basalMetabolism(60.0, 165.0, 30.0, 0.0),
            Health.basalMetabolism(60.0, 165.0, 30.0, -1.0),
            0.01,
        )
    }
}