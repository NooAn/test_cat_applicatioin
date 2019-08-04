package com.cat.bit.catapp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer

/**
 * Convert bitmap to byte array using ByteBuffer.
 */
fun Bitmap.convertToByteArrayUncompressed(): ByteArray {
    //minimum number of bytes that can be used to store this bitmap's pixels
    val size = this.byteCount

    //allocate new instances which will hold bitmap
    val buffer = ByteBuffer.allocate(size)
    val bytes = ByteArray(size)

    //copy the bitmap's pixels into the specified buffer
    this.copyPixelsToBuffer(buffer)

    //rewinds buffer (buffer position is set to zero and the mark is discarded)
    buffer.rewind()

    //transfer bytes from buffer into the given destination array
    buffer.get(bytes)

    return bytes
}

fun Bitmap.convertToByteArray(): ByteArray {
    val baos = ByteArrayOutputStream()
    baos.use {
        this.compress(Bitmap.CompressFormat.PNG, 100, it)
    }
    return baos.toByteArray()

}

fun ByteArray?.convertBitmapFromByteArray(): Bitmap? {
    return this?.size?.let { BitmapFactory.decodeByteArray(this, 0, it) }
}