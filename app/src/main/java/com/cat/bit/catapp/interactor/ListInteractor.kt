package com.cat.bit.catapp.interactor

import android.graphics.Bitmap
import android.util.Log
import com.bumptech.glide.request.FutureTarget
import com.cat.bit.catapp.convertBitmapFromByteArray
import com.cat.bit.catapp.entity.Cats
import com.cat.bit.catapp.repository.ListCatsRepository
import javax.inject.Inject


class ListInteractor @Inject constructor(private val repository: ListCatsRepository) {
    private val DEFAULT_ERROR_IMAGE =
        "https://img.medscape.com/thumbnail_library/dt_160608_error_sign_800x600.jpg"

    fun getListOfCat() = repository.getListOfCat().cache().onErrorReturnItem(listOf(Cats().apply {
        url = DEFAULT_ERROR_IMAGE
    }))

    fun makeBookmarkFromImage(futureTarget: FutureTarget<Bitmap>, url: String) =
        repository.bookmarkImage(futureTarget, url)

    fun saveBitmap(
        futureTarget: FutureTarget<Bitmap>,
        url: String
    ) = repository.saveBitmapInDownloads(futureTarget, url)

    fun getAllBookmarks() =
        repository
            .getAllBookmarImages()
            .flattenAsObservable { it }
            .map {
                it.apply { bitmap = image.convertBitmapFromByteArray() }
            }
            .toList()


}
