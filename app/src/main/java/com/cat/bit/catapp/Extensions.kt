package com.cat.bit.catapp

import android.graphics.Bitmap
import android.R.array
import android.R.attr.bitmap
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import java.nio.ByteBuffer
import android.R.attr.bitmap
import android.R.attr.name
import android.graphics.BitmapFactory


fun Bitmap.createByteArray(): ByteArray {
    val size = this.rowBytes * this.height
    val byteBuffer = ByteBuffer.allocate(size)
    this.copyPixelsToBuffer(byteBuffer)
    return byteBuffer.array()
}
/**
 * Convert bitmap to byte array using ByteBuffer.
 */
fun Bitmap.convertToByteArray(): ByteArray {
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

fun ByteArray.covertBitmapFromByteArray() : Bitmap {
    return BitmapFactory.decodeByteArray(this, 0, this.size);
}