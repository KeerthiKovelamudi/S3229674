package uk.ac.tees.mad.W9606817.Data.Local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "quotes")
data class Quote(
    @PrimaryKey(autoGenerate = true) val quoteid: Int = 0,
    val a: String,
    val c: String,
    val h: String,
    val q: String,
    val deviceDate : String
)