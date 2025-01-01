package uk.ac.tees.mad.quotesapp.data.Favorites

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface FavoriteDao {


    @Query("SELECT * FROM favorite_quotes")
    fun getAllFavorites () : List<FavoriteQuotes>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavorite (favoriteQuotes: FavoriteQuotes)

    @Query("Select * from favorite_quotes WHERE q  LIKE '%' || :content || '%'")
    suspend fun searchFavorites(content : String) : List<FavoriteQuotes>

    @Query("Select * from favorite_quotes ORDER BY q ASC")
    suspend fun sortByASC() : List<FavoriteQuotes>

    @Query("Select * from favorite_quotes ORDER BY q DESC")
    suspend fun sortByDesc() : List<FavoriteQuotes>

    @Delete
    suspend fun deleteFav(favoriteQuotes: FavoriteQuotes)
}