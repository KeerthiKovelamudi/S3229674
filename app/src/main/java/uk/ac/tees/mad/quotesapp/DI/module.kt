package uk.ac.tees.mad.quotesapp.DI

import android.content.Context
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory.Companion.invoke
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uk.ac.tees.mad.quotesapp.data.Favorites.FavoriteDao
import uk.ac.tees.mad.quotesapp.data.Favorites.FavoriteDatabase
import uk.ac.tees.mad.quotesapp.data.Local.QuoteDao
import uk.ac.tees.mad.quotesapp.data.Local.QuoteDatabase
import uk.ac.tees.mad.quotesapp.data.Remote.QuoteService

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): QuoteDatabase {
        return QuoteDatabase.getDatabase(context)
    }

    @Provides
    fun provideQuoteDao(database: QuoteDatabase): QuoteDao {
        return database.quoteDao()
    }

    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://zenquotes.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    @Provides
    fun providesQuoteService(retrofit: Retrofit): QuoteService {
        return retrofit.create(QuoteService::class.java)
    }

    @Provides
    fun provideFavoriteDatabase(
        @ApplicationContext context: Context
    ): FavoriteDatabase {
        return FavoriteDatabase.getFavoriteDatabase(context)
    }

    @Provides
    fun provideFavoriteDao(database: FavoriteDatabase): FavoriteDao {
        return database.favoriteDao()
    }

    @Provides
    fun providesAuthentication(): FirebaseAuth = Firebase.auth

    @Provides
    fun provideFirestore(): FirebaseFirestore = Firebase.firestore

}