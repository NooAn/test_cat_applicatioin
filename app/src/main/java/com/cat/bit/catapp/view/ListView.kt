package com.cat.bit.catapp.view

import com.cat.bit.catapp.Cats
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface ListView : MvpView {
    fun showList(list: List<Cats>)
    fun showLoading()
    fun hideLoading()
    fun hideNoInternetConnection()
    fun showNoInternetConnection()
}