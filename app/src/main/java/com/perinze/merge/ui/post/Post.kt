package com.perinze.merge.ui.post

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import com.perinze.merge.ui.favorite.Favorite

@Entity
data class Post(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "body") val body: String?,
)

@Dao
interface PostDao {
    @Query("SELECT * FROM post")
    fun getAll(): List<Post>

    @Insert
    fun insertAll(vararg post: Post)

    @Query("DELETE FROM post")
    fun deleteAll()

    @Query("SELECT * FROM post WHERE id = :id")
    fun getAllById(id: Int): List<Post>

    @Query("DELETE FROM post WHERE id = :id")
    fun deleteById(id: Int)
}