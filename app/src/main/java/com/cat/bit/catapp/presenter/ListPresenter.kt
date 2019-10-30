package com.cat.bit.catapp.presenter

import android.graphics.Bitmap
import android.net.NetworkInfo
import com.bumptech.glide.request.FutureTarget
import com.cat.bit.catapp.interactor.ListInteractor
import com.cat.bit.catapp.network.ConnectivityInteractor
import com.cat.bit.catapp.view.ListView
import com.github.pwittchen.reactivenetwork.library.rx2.Connectivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class ListPresenter constructor(
    private val connectivity: ConnectivityInteractor,
    private val interactor: ListInteractor
) :
    MvpPresenter<ListView>() {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showLoading()
        initNetworkStateListening()
    }

    fun loadCats() {
        compositeDisposable.add(
            interactor.getListOfCat()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally { viewState.hideLoading() }
                .subscribe({
                    viewState.showList(it)
                }, {
                    viewState.showNotification("List of cats was not downloaded => ${it.message}")
                    viewState.hideLoading()
                })
        )
    }

    private fun onConnectivityChanged(connectivity: Connectivity) {
        if (connectivity.state == NetworkInfo.State.CONNECTED) {
            viewState.hideNoInternetConnection()
        } else {
            viewState.showNoInternetConnection()
        }
    }

    private fun initNetworkStateListening() {
        compositeDisposable.add(
            connectivity.connectivityObservable
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ onConnectivityChanged(it) }, {})
        )
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    fun saveImage(
        futureTarget: FutureTarget<Bitmap>,
        url: String
    ) {
        compositeDisposable.add(
            interactor.saveBitmap(futureTarget, url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ file ->
                    viewState.showNotificationImageSaved(
                        "The file has been saved in your default download location",
                        file
                    )
                }, {
                    viewState.showNotification("Image has not saved ${it.message}")
                })
        )
    }

    fun makeBookmark(futureTarget: FutureTarget<Bitmap>, url: String) {
        compositeDisposable.add(interactor.makeBookmarkFromImage(futureTarget, url)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe {
                viewState.showNotification("Image was added")
            })
    }
}
