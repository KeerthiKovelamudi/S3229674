package uk.ac.tees.mad.quotesapp.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import uk.ac.tees.mad.quotesapp.MainViewModel
import uk.ac.tees.mad.quotesapp.navigation.NavigateInApp


@Composable
fun RegistrationScreen(vm: MainViewModel, navController: NavController) {
    val isLoading = vm.isLoading
    val loggedIn = vm.isSignedIn

    if (loggedIn.value) {
        navController.navigate(NavigateInApp.TODAYSCREEN.route) {
            popUpTo(0)
        }
    }
    val email = remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Register", fontSize = 30.sp)
            Spacer(Modifier.height(24.dp))
            OutlinedTextField(
                value = email.value,
                onValueChange = { email.value = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = { Text("password") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    if (email.value.isNotEmpty() && password.value.isNotEmpty()) {
                        vm.signUp(context = context, email = email.value, password = password.value)
                        email.value = ""
                        password.value = ""
                    } else {
                        Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                if (isLoading.value) {
                    CircularProgressIndicator(color = Color.White)
                } else {
                    Text(text = "Sign Up")
                }
            }
        }
    }
}