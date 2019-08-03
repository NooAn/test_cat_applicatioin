package com.cat.bit.catapp.network

import com.github.pwittchen.reactivenetwork.library.rx2.Connectivity
import io.reactivex.Observable
import javax.inject.Inject

class ConnectivityInteractor @Inject constructor(private val networkConnectionHelper: NetworkConnectionHelper) {

    val connectivityObservable: Observable<Connectivity>
        get() = networkConnectionHelper.connectivityObservable

    val currentConnectivity: Connectivity
        get() = networkConnectionHelper.currentConnectivity

    val isConnectedToInternet: Boolean
        get() = networkConnectionHelper.isConnectedToInternet
}