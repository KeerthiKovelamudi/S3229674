package uk.ac.tees.mad.quotesapp.data.Favorites

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteQuotes::class], version = 1, exportSchema = false)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao

    companion object{
        @Volatile
        private var Instance : FavoriteDatabase? = null

        fun getFavoriteDatabase(context: Context): FavoriteDatabase {
            return Instance?: synchronized(this){
                Room.databaseBuilder(context, FavoriteDatabase::class.java,"favorite_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }

}