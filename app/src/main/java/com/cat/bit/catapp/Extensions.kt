package com.cat.bit.catapp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream

fun Bitmap.convertToByteArray(): ByteArray {
    val stream = ByteArrayOutputStream()
    stream.use {
        this.compress(Bitmap.CompressFormat.PNG, 100, it)
    }
    return stream.toByteArray()

}

fun ByteArray?.convertBitmapFromByteArray(): Bitmap? {
    return this?.size?.let { BitmapFactory.decodeByteArray(this, 0, it) }
}