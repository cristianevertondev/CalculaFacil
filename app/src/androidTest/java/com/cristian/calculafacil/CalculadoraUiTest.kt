package com.cristian.calculafacil

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CalculadoraUiTest {

    @get:Rule
    val rule = createAndroidComposeRule<MainActivity>()

    @Test
    fun homeShowsTitle() {
        rule.onNodeWithText("CalculaFácil").assertExists()
    }

    @Test
    fun percentCalculatorComputes() {
        rule.onNodeWithText("Porcentagem").performClick()
        rule.onNodeWithText("Valor").performTextInput("500")
        rule.onNodeWithText("Porcentagem (%)").performTextInput("5")
        rule.onNodeWithText("Calcular").performClick()
        rule.onNodeWithText("R$ 25,00").assertExists()
    }

    @Test
    fun invalidInputShowsError() {
        rule.onNodeWithText("Porcentagem").performClick()
        rule.onNodeWithText("Valor").performTextInput("abc")
        rule.onNodeWithText("Calcular").performClick()
        rule.onNodeWithText("Digite um número válido.").assertExists()
    }
}