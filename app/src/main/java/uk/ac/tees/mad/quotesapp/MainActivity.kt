package uk.ac.tees.mad.quotesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dagger.hilt.android.AndroidEntryPoint
import uk.ac.tees.mad.quotesapp.navigation.AppNavigation
import uk.ac.tees.mad.quotesapp.ui.theme.QuotesAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var darkTheme by remember {
                mutableStateOf(false)
            }
            QuotesAppTheme(darkTheme = darkTheme) {
                AppNavigation(callDark = { darkTheme = !darkTheme })
            }
        }
    }
}