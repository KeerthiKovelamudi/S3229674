package uk.ac.tees.mad.quotesapp.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import uk.ac.tees.mad.quotesapp.MainViewModel
import uk.ac.tees.mad.quotesapp.QuoteView
import uk.ac.tees.mad.quotesapp.SortDialog


@Composable
fun Archive(vm: MainViewModel, navController: NavController) {
    val quotes = vm.quotes.observeAsState(initial = emptyList())
    val searchBox = remember { mutableStateOf("") }
    val filterBox = remember{ mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        if (filterBox.value) {
            SortDialog(onDismiss = { filterBox.value = false }, onAscendingClick = { vm.sortByASC() }, onDescendingClick = { vm.sortByDESC() })
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp, start = 0.dp)
        ) {
            Text(
                text = "Archived Quotes",
                fontSize = 30.sp,
                modifier = Modifier
                    .padding(start = 5.dp)
                    .weight(1f)
            )
            Icon(imageVector = Icons.Rounded.Edit, contentDescription = null, modifier = Modifier
                .size(30.dp)
                .clickable { filterBox.value = true })
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)) {
            OutlinedTextField(value = searchBox.value, onValueChange = { searchBox.value = it },
                label = {
                    Text(text = "Search")
                },modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.Rounded.Search, contentDescription = null, modifier = Modifier
                .size(70.dp)
                .clickable { vm.searchQuotes(searchBox.value) })
        }
        LazyColumn {
            items(quotes.value) { item ->
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)) {
                    QuoteView(content = item.q, author = item.a, date = item.deviceDate, onFavClick = {
                        vm.addFavorites(item)
                    })
                }
            }
        }
    }
}