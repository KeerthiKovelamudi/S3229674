package uk.ac.tees.mad.quotesapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uk.ac.tees.mad.quotesapp.MainViewModel
import kotlin.collections.find
import kotlin.let
import kotlin.text.toInt

@Composable
fun Detail(vm: MainViewModel, quoteId: String) {
    val quotes = vm.quotesFromToday.observeAsState(initial = emptyList())
    val quote = quotes.value.find { it.quoteid == quoteId.toInt() }
    quote?.let {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp, start = 16.dp, end = 16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp) // Applying elevation
        ) {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(16.dp)
            ) {
                Text(text = it.q, fontSize = 20.sp)
                Text(
                    text = "Author: ${it.a}",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(
                    text = "Date: ${it.deviceDate}",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(
                    text = "Author Slug: ${it.h}",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    } ?: run {
        Text(text = "Quote not found", modifier = Modifier.padding(16.dp))
    }
}