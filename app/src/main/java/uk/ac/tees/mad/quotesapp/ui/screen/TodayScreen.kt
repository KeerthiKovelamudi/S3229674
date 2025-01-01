package uk.ac.tees.mad.quotesapp.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import uk.ac.tees.mad.quotesapp.MainViewModel
import uk.ac.tees.mad.quotesapp.MenuDialog
import uk.ac.tees.mad.quotesapp.QuoteView
import uk.ac.tees.mad.quotesapp.navigation.NavigateInApp

@Composable
fun TodayScreen(vm: MainViewModel, navController: NavController, callDark: () -> Unit) {
    val isMenuExpanded = remember { mutableStateOf(false) }
    val quotes = vm.quotesFromToday.observeAsState(initial = emptyList())

    Box(modifier = Modifier
        .fillMaxSize()
        .pointerInput(Unit) {
            detectHorizontalDragGestures { _, delta ->
                if (delta > 0) {
                    navController.navigate(NavigateInApp.YESTERDAYSCREEN.route)
                }
            }
        }
    ) {
        if (isMenuExpanded.value) {
            MenuDialog(navController, onDismiss = { isMenuExpanded.value = false })
        }

        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 36.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Quote of the day",
                    fontSize = 24.sp,
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .weight(1f)
                )
                Button(onClick = { callDark() }) {
                    Text(text = "Dark Theme")
                }
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = null,
                    modifier = Modifier
                        .size(36.dp)
                        .clickable {
                            isMenuExpanded.value = true
                        })
            }
            Column {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(quotes.value) { item ->
                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                            .clickable {
                                navController.navigate("detail/${item.quoteid}")
                            }) {
                            QuoteView(
                                content = item.q,
                                author = item.a,
                                date = item.deviceDate,
                                onFavClick = {
//                                    vm.addFavorites(item)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

