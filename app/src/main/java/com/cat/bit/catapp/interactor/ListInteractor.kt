package com.cat.bit.catapp.interactor

import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import com.bumptech.glide.request.FutureTarget
import com.cat.bit.catapp.entity.Cats
import com.cat.bit.catapp.repository.ListCatsRepository
import io.reactivex.Single
import java.io.File
import java.io.FileOutputStream
import java.net.URI
import javax.inject.Inject


class ListInteractor @Inject constructor(private val repository: ListCatsRepository) {
    private val DEFAULT_ERROR_IMAGE =
        "https://img.medscape.com/thumbnail_library/dt_160608_error_sign_800x600.jpg"

    fun getListOfCat() = repository.getListOfCat().cache().onErrorReturnItem(listOf(Cats().apply {
        url = DEFAULT_ERROR_IMAGE
    }))

    fun saveBitmap(
        futureTarget: FutureTarget<Bitmap>,
        url: String
    ) =
        Single.fromCallable { futureTarget.get() }
            .map { bitmap ->
                val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                val file =
                    File(
                        path,
                        URI(url).path.toString().replace("/","_")
                    )
                FileOutputStream(file).use {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 85, it)
                }
                path
            }
            .doFinally { futureTarget.cancel(false) }

}