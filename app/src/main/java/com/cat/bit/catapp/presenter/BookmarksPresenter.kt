package com.cat.bit.catapp.presenter

import com.cat.bit.catapp.interactor.ListInteractor
import com.cat.bit.catapp.view.BookmarksView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class BookmarksPresenter @Inject constructor(private val interactor: ListInteractor) :
    MvpPresenter<BookmarksView>() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadAllImages()
    }

    private fun loadAllImages() {
        compositeDisposable.add(
            interactor.getAllBookmarks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewState.showList(it)
                }, {

                })
        )
    }

}
