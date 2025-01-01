package uk.ac.tees.mad.quotesapp.navigation

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import uk.ac.tees.mad.quotesapp.ui.screen.AuthenticationScreen
import uk.ac.tees.mad.quotesapp.MainViewModel
import uk.ac.tees.mad.quotesapp.ui.screen.Detail
import uk.ac.tees.mad.quotesapp.ui.screen.LoginScreen
import uk.ac.tees.mad.quotesapp.ui.screen.RegistrationScreen
import uk.ac.tees.mad.quotesapp.ui.screen.SplashScreen
import uk.ac.tees.mad.quotesapp.ui.screen.TodayScreen


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

            composable(NavigateInApp.AUTHENTICATIONSCREEN.route) {
                AuthenticationScreen(vm, navController)
            }

            composable(NavigateInApp.REGISTER.route) {
                RegistrationScreen(vm, navController)
            }

            composable(NavigateInApp.LOGIN.route) {
                LoginScreen(vm, navController)
            }
            composable(NavigateInApp.TODAYSCREEN.route) {
                TodayScreen(vm = vm, navController = navController, callDark = callDark)
            }

            composable(
                NavigateInApp.DETAIL.route,
                arguments = listOf(navArgument("quoteId") { type = NavType.StringType })
            ) { backStackEntry ->
                val quoteId = backStackEntry.arguments?.getString("quoteId") ?: ""
                Detail(vm, quoteId)
            }
        }
    }
}