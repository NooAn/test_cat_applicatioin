package com.cat.bit.catapp

import android.content.Context
import android.net.NetworkInfo
import com.github.pwittchen.reactivenetwork.library.rx2.Connectivity
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import io.reactivex.Observable

class NetworkConnectionHelper(private val context: Context) {

    val connectivityObservable: Observable<Connectivity>
        get() = ReactiveNetwork.observeNetworkConnectivity(context)

    val currentConnectivity: Connectivity
        get() = Connectivity.create(context)

    val isConnectedToInternet: Boolean
        get() {
            val networkState = currentConnectivity.state
            return networkState == NetworkInfo.State.CONNECTED || networkState == NetworkInfo.State.CONNECTING
        }
}
