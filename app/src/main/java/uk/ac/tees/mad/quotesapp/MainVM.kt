package uk.ac.tees.mad.quotesapp

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import uk.ac.tees.mad.W9606817.Data.Local.Quote
import uk.ac.tees.mad.quotesapp.Repository.QuoteRepository
import uk.ac.tees.mad.quotesapp.data.Favorites.FavoriteQuotes
import javax.inject.Inject
import kotlin.toString

@HiltViewModel
class MainViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
    private val repository: QuoteRepository,
) : ViewModel() {
    val currentUser = auth.currentUser
    private val _loading = MutableStateFlow(true)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()


    private val _favorites = MutableLiveData<List<FavoriteQuotes>>()
    val favorites: LiveData<List<FavoriteQuotes>> get() = _favorites

    private val _quotes = MutableLiveData<List<Quote>>()
    val quotes: LiveData<List<Quote>> get() = _quotes

    private val _quotesFromYesterday = MutableLiveData<List<Quote>>()
    val quotesFromYesterday: LiveData<List<Quote>> get() = _quotesFromYesterday

    private val _quotesFromToday = MutableLiveData<List<Quote>>()
    val quotesFromToday: LiveData<List<Quote>> get() = _quotesFromToday

    val isSignedIn = mutableStateOf(false)
    val isLoading = mutableStateOf(false)


    init {
        viewModelScope.launch {
            delay(1000L)
            _loading.emit(false)
        }
        getandStore()
        loadFavorites()
    }

    fun getandStore() {
        viewModelScope.launch(Dispatchers.IO) {

            try {
                repository.getQuotesAndStore()
            } catch (e: Exception) {
                Log.d("MainViewModel", "Error fetching quotes ${e.localizedMessage}")
            }
            quotesFromTodays()
            quotesFromYesterday()
            loadAllQuotes()
        }
    }

    fun quotesFromTodays() {
        viewModelScope.launch(Dispatchers.IO) {
            val quotesfromtoday = repository.getQuotesFromToday()
            _quotesFromToday.postValue(quotesfromtoday)
            Log.d("Todays Quote", quotesfromtoday.toString())
        }
    }

    fun quotesFromYesterday() {
        viewModelScope.launch(Dispatchers.IO) {
            val quotesFromYesterday = repository.getQuotesFromYesterday()
            _quotesFromYesterday.postValue(quotesFromYesterday)
            Log.d("Yesterday Quote", quotesFromYesterday.toString())
        }
    }

    private fun loadAllQuotes() {

        viewModelScope.launch {
            _quotes.value = repository.getAllQuotes()
            Log.d("_quotesValue", _quotes.value.toString())
            Log.d("Todays Quote", _quotesFromToday.value.toString())
        }
    }

    fun sortByASC(){
        viewModelScope.launch(Dispatchers.IO) {
            val sortedQuotes = repository.sortByASC()
            _quotes.postValue(sortedQuotes)
        }
    }

    fun sortByDESC(){
        viewModelScope.launch(Dispatchers.IO) {
            val sortedQuotes = repository.sortByDESC()
            _quotes.postValue(sortedQuotes)
        }
    }

    fun sortByFavASC(){
        viewModelScope.launch(Dispatchers.IO) {
            val sortedQuotes = repository.sortByFavASC()
            _favorites.postValue(sortedQuotes)
        }
    }

    fun sortByFavDESC(){
        viewModelScope.launch(Dispatchers.IO) {
            val sortedQuotes = repository.sortByFavDESC()
            _favorites.postValue(sortedQuotes)
        }
    }

    fun searchQuotes(content: String){
        viewModelScope.launch(Dispatchers.IO) {
            val searchResult = repository.searchQuotes(content)
            Log.d("Search Result", searchResult.toString())
            _quotes.postValue(searchResult)
        }
    }

    fun addFavorites(favQuote: Quote) {
        val result = mapEntity(favQuote)
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFavorites(result)
        }
        loadFavorites()

    }

    fun loadFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            val favorites = repository.getFavorites()
            _favorites.postValue(favorites)
        }
    }

    fun mapEntity(quote: Quote): FavoriteQuotes {
        return FavoriteQuotes(
            c = quote.c,
            q = quote.q,
            a = quote.a,
            h = quote.h,
            deviceDate = quote.deviceDate
        )
    }

    fun searchFavorites(query: String){
        Log.d("Search Query", query)
        viewModelScope.launch(Dispatchers.IO) {
            val searchResult = repository.searchFavorites(query)
            Log.d("Search Result", searchResult.toString())
            _favorites.postValue(searchResult)
        }
    }

    fun deleteFav(fav: FavoriteQuotes){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFav(fav)
        }
        loadFavorites()
    }

    fun signUp(context: Context, email: String, password: String) {
        isLoading.value = true

        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            val userData = hashMapOf("email" to email)
            db.collection("User").document(email).set(userData)
            Toast.makeText(context, "Sign Up Successfull", Toast.LENGTH_SHORT).show()
            isSignedIn.value = true
            isLoading.value = false
        }.addOnFailureListener { e ->
            Log.d("TAG", "Signup failed ${e.localizedMessage}")
            Toast.makeText(context, e.localizedMessage, Toast.LENGTH_LONG).show()
            isLoading.value = false
        }
    }

    fun logIn(context: Context, email: String, password: String) {
        isLoading.value = true
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            isSignedIn.value = true
            isLoading.value = false
            Toast.makeText(context, "Logged In successfully", Toast.LENGTH_SHORT).show()
        }
            .addOnFailureListener {
                isLoading.value = false
                Toast.makeText(context, it.localizedMessage, Toast.LENGTH_SHORT).show()
            }
    }
}