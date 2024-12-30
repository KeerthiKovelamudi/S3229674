package uk.ac.tees.mad.quotesapp.navigation

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uk.ac.tees.mad.quotesapp.MainViewModel
import uk.ac.tees.mad.quotesapp.ui.screen.SplashScreen


enum class NavigateInApp(val route: String) {
    SPLASHSCREEN("splashscreen"),
    AUTHENTICATIONSCREEN("authenticationScreen"),
    LOGIN("login"),
    REGISTER("register"),
    TODAYSCREEN("todayScreen"),
    YESTERDAYSCREEN("yesterdayScreen"),
    DETAIL("detail/{quoteId}"),
    FAVORITE("favorite"),
    ARCHIVE("archive"),
}

@Composable
fun AppNavigation(callDark: () -> Unit) {
    val navController = rememberNavController()
    val vm: MainViewModel = viewModel()
    Surface {
        NavHost(
            navController = navController,
            startDestination = NavigateInApp.SPLASHSCREEN.route
        ) {

            composable(NavigateInApp.SPLASHSCREEN.route) {
                SplashScreen(vm = vm, navController = navController)
            }

        }
    }
}