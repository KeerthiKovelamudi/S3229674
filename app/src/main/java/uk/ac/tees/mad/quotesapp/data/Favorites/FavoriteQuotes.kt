package uk.ac.tees.mad.quotesapp.data.Favorites

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_quotes")
data class FavoriteQuotes(
    @PrimaryKey(autoGenerate = true) val quoteid: Int = 0,
    val a: String,
    val c: String,
    val h: String,
    val q: String,
    val deviceDate : String
)
