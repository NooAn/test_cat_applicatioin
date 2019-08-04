package com.cat.bit.catapp.view

import com.cat.bit.catapp.entity.Cats
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import java.io.File

@StateStrategyType(AddToEndSingleStrategy::class)
interface ListView : MvpView {

    fun showList(list: List<Cats>)

    fun showLoading()

    fun hideLoading()

    fun hideNoInternetConnection()

    fun showNoInternetConnection()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showNotificationImageSaved(s: String, f: File)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showNotification(s: String)
}
