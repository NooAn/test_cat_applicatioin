package com.cat.bit.catapp.presenter

import android.util.Log
import com.cat.bit.catapp.interactor.ListInteractor
import com.cat.bit.catapp.view.BookmarksView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class BookmarksPresenter constructor(private val interactor: ListInteractor) :
    MvpPresenter<BookmarksView>() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun loadAllImages() {
        compositeDisposable.add(
            interactor.getAllBookmarks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.size == 0) viewState.showSorry() else
                        viewState.showList(it)
                }, {
                    Log.e("LOG", "Presenter bookmarks said:", it)
                })
        )
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

}
