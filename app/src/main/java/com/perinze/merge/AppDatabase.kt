package com.perinze.merge

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.perinze.merge.ui.favorite.Favorite
import com.perinze.merge.ui.favorite.FavoriteDao
import com.perinze.merge.ui.post.Post
import com.perinze.merge.ui.post.PostDao

@Database(entities = [Favorite::class, Post::class], version = AppDatabase.VERSION)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
    abstract fun postDao(): PostDao

    companion object {
        private const val TAG = "AppDatabase"

        const val VERSION = 1
        private const val DATABASE_NAME = "merge"

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java, DATABASE_NAME
            ).allowMainThreadQueries().build()
        }
    }
}
