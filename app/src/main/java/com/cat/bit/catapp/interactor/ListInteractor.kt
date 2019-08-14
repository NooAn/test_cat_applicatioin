package com.cat.bit.catapp.interactor

import android.graphics.Bitmap
import com.bumptech.glide.request.FutureTarget
import com.cat.bit.catapp.convertBitmapFromByteArray
import com.cat.bit.catapp.entity.Bookmark
import com.cat.bit.catapp.entity.Cats
import com.cat.bit.catapp.repository.IListCatsRepository
import com.cat.bit.catapp.repository.ListCatsRepository
import io.reactivex.Completable
import io.reactivex.Single
import java.io.File
import javax.inject.Inject

interface IListInteractor {
    fun getListOfCat(): Single<List<Cats>>
    fun makeBookmarkFromImage(futureTarget: FutureTarget<Bitmap>, url: String): Completable
    fun saveBitmap(
        futureTarget: FutureTarget<Bitmap>,
        url: String
    ): Single<File>

    fun getAllBookmarks(): Single<MutableList<Bookmark>>
}


class ListInteractor @Inject constructor(private val repository: ListCatsRepository) :
    IListInteractor {
    private val DEFAULT_ERROR_IMAGE =
        "https://img.medscape.com/thumbnail_library/dt_160608_error_sign_800x600.jpg"

    override fun getListOfCat() =
        repository.getListOfCat().cache().onErrorReturnItem(listOf(Cats().apply {
            url = DEFAULT_ERROR_IMAGE
        }))

    override fun makeBookmarkFromImage(futureTarget: FutureTarget<Bitmap>, url: String) =
        repository.bookmarkImage(futureTarget, url)

    override fun saveBitmap(
        futureTarget: FutureTarget<Bitmap>,
        url: String
    ) = repository.saveBitmapInDownloads(futureTarget, url)

    override fun getAllBookmarks() =
        repository
            .getAllBookmarkImages()
            .flattenAsObservable { it }
            .map {
                it.apply { bitmap = image.convertBitmapFromByteArray() }
            }
            .toList()


}
