package com.cat.bit.catapp.repository

import android.graphics.Bitmap
import android.os.Environment
import com.bumptech.glide.request.FutureTarget
import com.cat.bit.catapp.convertToByteArray
import com.cat.bit.catapp.entity.Bookmark
import com.cat.bit.catapp.network.CatApi
import com.cat.bit.catapp.entity.Cats
import com.cat.bit.catapp.room.BookmarkDao
import io.reactivex.Completable
import io.reactivex.Single
import java.io.File
import java.io.FileOutputStream
import java.net.URI
import javax.inject.Inject


interface IListCatsRepository {
    fun getListOfCat(): Single<List<Cats>>
    fun bookmarkImage(
        bitmap: FutureTarget<Bitmap>,
        url: String
    ): Completable

    fun getAllBookmarkImages(): Single<List<Bookmark>>
    fun saveBitmapInDownloads(futureTarget: FutureTarget<Bitmap>, url: String): Single<File>
}

class ListCatsRepository @Inject constructor(val api: CatApi, val db: BookmarkDao) :
    IListCatsRepository {
    private val LIMIT = 10

    override fun getAllBookmarkImages() = db.getAll()

    override fun saveBitmapInDownloads(
        futureTarget: FutureTarget<Bitmap>,
        url: String
    ) = Single.fromCallable { futureTarget.get() }
        .map { bitmap ->
            val path =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val file =
                File(
                    path,
                    URI(url).path.toString().replace("/", "_")
                )
            FileOutputStream(file).use {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 85, it)
            }
            path
        }
        .doFinally { futureTarget.cancel(false) }


    override fun getListOfCat() = api.getCats(LIMIT)

    override fun bookmarkImage(
        bitmap: FutureTarget<Bitmap>,
        url: String
    ): Completable =
        Completable.fromAction { db.insertImage(Bookmark(image = bitmap.get().convertToByteArray())) }

}



