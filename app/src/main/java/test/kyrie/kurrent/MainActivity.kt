package test.kyrie.kurrent

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import test.kyrie.core.theme.KurrentTheme
import test.kyrie.feature.currency_list.ui.CurrencyListScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KurrentTheme {
                CurrencyListScreen(
                    onNavigateToCalculator = {
                        // TODO: Navigate to calculator screen
                    },
                )
            }
        }
    }
}