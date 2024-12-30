package uk.ac.tees.mad.quotesapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import uk.ac.tees.mad.quotesapp.MainViewModel
import uk.ac.tees.mad.quotesapp.checkSignedIn
import uk.ac.tees.mad.quotesapp.navigation.NavigateInApp

@Composable
fun AuthenticationScreen(vm: MainViewModel, navController: NavController) {
    checkSignedIn(vm, navController)
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { navController.navigate(NavigateInApp.REGISTER.route) }) {
                Text(text = "Sign up")
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { navController.navigate(NavigateInApp.LOGIN.route) }) {
                Text(text = "Log in")
            }
        }
    }
}