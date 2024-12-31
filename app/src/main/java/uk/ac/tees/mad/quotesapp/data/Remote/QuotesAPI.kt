package uk.ac.tees.mad.quotesapp.data.Remote

import retrofit2.Response
import retrofit2.http.GET

interface QuoteService {
    @GET("api/quotes")
    suspend fun getQuote(): Response<List<QuoteItem>>
}
