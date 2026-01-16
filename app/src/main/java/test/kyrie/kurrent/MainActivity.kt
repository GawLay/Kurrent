package test.kyrie.kurrent

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import test.kyrie.core.theme.KurrentTheme
import test.kyrie.kurrent.navigation.KurrentNavGraph

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KurrentTheme {
                KurrentNavGraph()
            }
        }
    }
}