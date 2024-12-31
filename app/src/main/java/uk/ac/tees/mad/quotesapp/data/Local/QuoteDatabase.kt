package uk.ac.tees.mad.quotesapp.data.Local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uk.ac.tees.mad.W9606817.Data.Local.Quote
import kotlin.also

@Database(entities = [Quote::class], version = 2)
abstract class QuoteDatabase : RoomDatabase() {
    abstract fun quoteDao(): QuoteDao

    companion object{
        @Volatile
        private var Instance : QuoteDatabase? = null

        fun getDatabase(context: Context):QuoteDatabase {
            return Instance?: synchronized(this){
                Room.databaseBuilder(context,QuoteDatabase::class.java,"quote_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}