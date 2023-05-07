package com.perinze.merge.ui.favorite

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import androidx.room.*

@Entity
data class Favorite(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "url") val url: String?,
)

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite")
    fun getAll(): List<Favorite>

    @Insert
    fun insertAll(vararg favorite: Favorite)

    @Query("DELETE FROM favorite")
    fun deleteAll()

    @Query("SELECT * FROM favorite WHERE id = :id")
    fun getAllById(id: Int): List<Favorite>

    @Query("DELETE FROM favorite WHERE id = :id")
    fun deleteById(id: Int)

    @Query("SELECT * FROM favorite WHERE url = :url")
    fun getAllByUrl(url: String): List<Favorite>
}