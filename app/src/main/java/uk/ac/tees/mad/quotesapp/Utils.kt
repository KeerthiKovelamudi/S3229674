package uk.ac.tees.mad.quotesapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import uk.ac.tees.mad.quotesapp.navigation.NavigateInApp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


@Composable
fun CommonProgressBar() {
    CircularProgressIndicator()
}

fun checkSignedIn(vm: MainViewModel, navController: NavController) {
    val currentUser = vm.currentUser

    if (currentUser != null) {
        navController.navigate(NavigateInApp.TODAYSCREEN.route) {
            popUpTo(0)
        }
    }
}

fun getYesterdayDate(): String {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DATE, -1)
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return sdf.format(calendar.time)
}

fun getTodayDate(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return sdf.format(Date())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuDialog(navController: NavController, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onDismiss.invoke() },
        modifier = Modifier
            .width(200.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .clickable {
                        navController.navigate(NavigateInApp.FAVORITE.route)
                    }
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween

            ) {
                Text(text = "Favorites", fontSize = 20.sp)
                Icon(imageVector = Icons.Rounded.Favorite, contentDescription = null)
            }

            Spacer(modifier = Modifier.height(30.dp))

            Row(modifier = Modifier
                .clickable {
                    navController.navigate(NavigateInApp.ARCHIVE.route)
                }
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Archive", fontSize = 20.sp)
                Icon(imageVector = Icons.Rounded.Star, contentDescription = null)
            }
        }
    }
}

@Composable
fun QuoteView(content: String, author: String, date: String, onFavClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp) // Applying elevation
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(8.dp)
        ) {
            Text(text = content, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Row {
                Text(
                    text = "~ ${author}", modifier = Modifier
                        .weight(1f)
                        .padding(4.dp)
                )
                Text(text = "-${date}", modifier = Modifier.padding(4.dp))
                Icon(imageVector = Icons.Rounded.Favorite,
                    contentDescription = null,
                    tint = Color.Red,
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .clickable {
                            onFavClick()
                        })
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortDialog(onDismiss: () -> Unit, onAscendingClick: () -> Unit, onDescendingClick: () -> Unit){
    AlertDialog(onDismissRequest = { onDismiss.invoke() }){
        Column(modifier = Modifier
            .padding(16.dp)
            .size(250.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Sort The Items")
            Text(text = "Sort by Quote Ascending", fontSize = 20.sp, modifier = Modifier
                .background(Color.LightGray)
                .clickable {
                    onAscendingClick()
                })
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = "Sort by Quote Descending", fontSize = 20.sp, modifier = Modifier
                .background(Color.LightGray)
                .clickable {
                    onDescendingClick()
                })
        }
    }
}
