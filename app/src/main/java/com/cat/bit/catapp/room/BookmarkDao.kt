package com.cat.bit.catapp.room

import androidx.room.*
import androidx.room.Database
import com.cat.bit.catapp.entity.Bookmark
import io.reactivex.Single

private const val DATABASE_VERSION = 1

@Dao
interface BookmarkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertImage(image: Bookmark)

    @Query("SELECT * FROM bookmarks")
    fun getAll(): Single<List<Bookmark>>
}

@Database(entities = [Bookmark::class], version = DATABASE_VERSION, exportSchema = false)
abstract class BookmarkDB : RoomDatabase() {
    abstract fun getBookmarkDao(): BookmarkDao
}
