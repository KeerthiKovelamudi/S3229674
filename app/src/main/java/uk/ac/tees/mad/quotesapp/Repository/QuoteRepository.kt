package uk.ac.tees.mad.quotesapp.Repository

import android.util.Log
import uk.ac.tees.mad.W9606817.Data.Local.Quote
import uk.ac.tees.mad.quotesapp.data.Favorites.FavoriteDao
import uk.ac.tees.mad.quotesapp.data.Favorites.FavoriteQuotes
import uk.ac.tees.mad.quotesapp.data.Local.QuoteDao
import uk.ac.tees.mad.quotesapp.data.Remote.QuoteService
import uk.ac.tees.mad.quotesapp.getTodayDate
import uk.ac.tees.mad.quotesapp.getYesterdayDate
import java.time.LocalDate
import javax.inject.Inject

class QuoteRepository @Inject constructor(
    private val quoteDao: QuoteDao,
    private val favoriteDao: FavoriteDao,
    private val quotesAPI: QuoteService,
) {
    suspend fun getQuotesAndStore() {
        val response = quotesAPI.getQuote()
        if (response.isSuccessful) {
            Log.d("Response", "Success")
            Log.d("Response", response.body().toString())
            val currentDate = LocalDate.now()
            Log.d("currentDate", currentDate.toString())
            response.body()?.let { quotes ->
                quoteDao.insertQuote(quotes.map {
                    Quote(
                        c = it.c,
                        a = it.a,
                        q = it.q,
                        h = it.h,
                        deviceDate = currentDate.toString()
                    )
                })
                Log.d("QuoteResponse", quotes.toString())
            }
        } else {
            Log.e("QuoteResponse", "Failed to fetch quotes")
        }
    }

    suspend fun getAllQuotes(): List<Quote> {
        return quoteDao.getallQuote()
    }

    suspend fun searchQuotes(query: String): List<Quote> {
        Log.d("Search Query", query)
        return quoteDao.searchQuotes(query)
    }

    suspend fun sortByASC(): List<Quote> {
        return quoteDao.sortByASC()
    }

    suspend fun sortByDESC(): List<Quote> {
        return quoteDao.sortByDesc()
    }

    fun getQuotesFromToday(): List<Quote> {
        val todayDate = getTodayDate()
        Log.d("todayDate", todayDate)
        return quoteDao.getQuotesFromToday(todayDate)
    }

    fun getQuotesFromYesterday(): List<Quote> {
        val yesterdayDate = getYesterdayDate()
        Log.d("Yesterday", yesterdayDate)
        return quoteDao.getQuotesFromYesterday(yesterdayDate)
    }

    suspend fun addFavorites(favorite: FavoriteQuotes) {
        favoriteDao.insertFavorite(favorite)
    }

    fun getFavorites(): List<FavoriteQuotes> {
        return favoriteDao.getAllFavorites()
    }

    suspend fun deleteFav(content: FavoriteQuotes) {
        favoriteDao.deleteFav(content)
    }

    suspend fun searchFavorites(query: String): List<FavoriteQuotes> {
        Log.d("Search Query", query)
        return favoriteDao.searchFavorites(query)
    }

}

