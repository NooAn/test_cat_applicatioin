package com.cat.bit.catapp.presenter

import android.net.NetworkInfo
import android.util.Log
import com.cat.bit.catapp.ListInteractor
import com.cat.bit.catapp.network.ConnectivityInteractor
import com.cat.bit.catapp.view.ListView
import com.github.pwittchen.reactivenetwork.library.rx2.Connectivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class ListPresenter @Inject constructor(
    private val connectivity: ConnectivityInteractor,
    private val interactor: ListInteractor
) :
    MvpPresenter<ListView>() {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        initNetworkStateListening()
        loadFirstCats()
    }

    private fun loadFirstCats() {
        compositeDisposable.add(
            interactor.getListOfCat()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d("LOG", "cats $it")
                    viewState.showList(it)
                }, {
                    Log.e("LOG", it.message)
                })
        )
    }

    private fun onConnectivityChanged(connectivity: Connectivity) {
        if (connectivity.state == NetworkInfo.State.CONNECTED || connectivity.state == NetworkInfo.State.CONNECTING) {
            viewState.hideNoInternetConnection()
        } else {
            viewState.showNoInternetConnection()
        }
    }

    private fun initNetworkStateListening() {
        compositeDisposable.add(
            connectivity.connectivityObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ onConnectivityChanged(it) }, {})
        )
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}