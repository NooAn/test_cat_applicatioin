package com.cat.bit.catapp.entity

import android.graphics.Bitmap
import android.util.Log
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.cat.bit.catapp.convertBitmapFromByteArray
import com.google.gson.annotations.SerializedName
import java.io.ByteArrayOutputStream
import java.io.IOException

@Entity(tableName = "bookmarks")
data class Bookmark(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "image")
    var image: ByteArray? = null,

    @Ignore
    var bitmap: Bitmap? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Bookmark

        if (id != other.id) return false
        if (image != null) {
            if (other.image == null) return false
            if (!image!!.contentEquals(other.image!!)) return false
        } else if (other.image != null) return false
        if (bitmap != other.bitmap) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + (image?.contentHashCode() ?: 0)
        result = 31 * result + (bitmap?.hashCode() ?: 0)
        return result
    }
}

