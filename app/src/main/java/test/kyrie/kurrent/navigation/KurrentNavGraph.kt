package test.kyrie.kurrent.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import test.kyrie.feature.calculator.ui.CalculatorScreen
import test.kyrie.feature.currency_list.ui.CurrencyListScreen

sealed class Screen(val route: String) {
    object CurrencyList : Screen("currency_list")
    object Calculator : Screen("calculator")
}

@Composable
fun KurrentNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.CurrencyList.route
    ) {
        composable(Screen.CurrencyList.route) {
            CurrencyListScreen(
                onNavigateToCalculator = {
                    navController.navigate(Screen.Calculator.route)
                }
            )
        }

        composable(Screen.Calculator.route) {
            CalculatorScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
