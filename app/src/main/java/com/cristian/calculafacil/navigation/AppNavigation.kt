package com.cristian.calculafacil.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.cristian.calculafacil.R
import com.cristian.calculafacil.model.CalculatorCatalog
import com.cristian.calculafacil.model.Converter
import com.cristian.calculafacil.model.ConverterRegistry
import com.cristian.calculafacil.ui.screens.calculator.CalculatorScreen
import com.cristian.calculafacil.ui.screens.converter.ConverterScreen
import com.cristian.calculafacil.ui.screens.home.HomeScreen

object Routes {
    const val HOME = "home"
    const val CALCULATOR = "calculator/{id}"
    const val CONVERTER = "converter/{id}"
    fun calculator(id: String) = "calculator/$id"
    fun converter(id: String) = "converter/$id"
}

/** Grafo de navegação do app. Transições leves para preservar desempenho. */
@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.HOME,
        enterTransition = {
            slideInHorizontally(tween(280)) { it / 10 } + fadeIn(tween(220))
        },
        exitTransition = { fadeOut(tween(180)) },
        popEnterTransition = { fadeIn(tween(220)) },
        popExitTransition = {
            slideOutHorizontally(tween(280)) { it / 10 } + fadeOut(tween(180))
        },
    ) {
        composable(Routes.HOME) {
            HomeScreen(
                onOpenTool = { tool ->
                    when (tool) {
                        is Converter -> navController.navigate(Routes.converter(tool.id))
                        else -> navController.navigate(Routes.calculator(tool.id))
                    }
                },
            )
        }
        composable(
            route = Routes.CALCULATOR,
            arguments = listOf(navArgument("id") { type = NavType.StringType }),
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id").orEmpty()
            val engine = CalculatorCatalog.byId(id)
            if (engine != null) {
                CalculatorScreen(
                    engine = engine,
                    onBack = { navController.popBackStack() },
                )
            } else {
                LaunchedEffect(Unit) {
                    navController.popBackStack(Routes.HOME, inclusive = false)
                }
            }
        }
        composable(
            route = Routes.CONVERTER,
            arguments = listOf(navArgument("id") { type = NavType.StringType }),
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id").orEmpty()
            val converter = ConverterRegistry.byId(id)
            if (converter != null) {
                ConverterScreen(
                    converter = converter,
                    onBack = { navController.popBackStack() },
                )
            } else {
                LaunchedEffect(Unit) {
                    navController.popBackStack(Routes.HOME, inclusive = false)
                }
            }
        }
    }
}