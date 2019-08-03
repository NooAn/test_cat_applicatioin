package com.cat.bit.catapp.room

import androidx.room.*
import androidx.room.Database
import kotlin.reflect.KClass

@Entity(tableName = "bookmarks")
data class Bookmark(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "image")
    val image: ByteArray? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Bookmark

        if (id != other.id) return false
        if (image != null) {
            if (other.image == null) return false
            if (!image.contentEquals(other.image)) return false
        } else if (other.image != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + (image?.contentHashCode() ?: 0)
        return result
    }
}

@Dao
interface BookmarkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertImage(image: Bookmark)

    @Query("SELECT * FROM bookmarks")
    fun getAll(): List<Bookmark>
}

@Database(entities = [Bookmark::class], version = 1, exportSchema = false)
abstract class BookmarkDB : RoomDatabase() {
    abstract fun getBookmarkDao(): BookmarkDao
}