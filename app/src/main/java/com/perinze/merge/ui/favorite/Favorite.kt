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
//) : Parcelable {
//    constructor(parcel: Parcel) : this(
//        parcel.readInt(),
//        parcel.readString(),
//        parcel.readString(),
//        parcel.readString()
//    )
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    override fun writeToParcel(p0: Parcel, p1: Int) {
//        p0.writeInt(uid)
//        p0.writeStringArray(arrayOf(heading, content, url))
//    }
//
//    companion object CREATOR : Parcelable.Creator<Article> {
//        override fun createFromParcel(parcel: Parcel): Article {
//            return Article(parcel)
//        }
//
//        override fun newArray(size: Int): Array<Article?> {
//            return arrayOfNulls(size)
//        }
//    }
//}

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

@Database(entities = [Favorite::class], version = AppDatabase.VERSION)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao

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