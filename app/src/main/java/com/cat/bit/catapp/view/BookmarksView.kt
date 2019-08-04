package com.cat.bit.catapp.view

import com.cat.bit.catapp.entity.Bookmark
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface BookmarksView : MvpView {

    fun showList(list: List<Bookmark>)
    
    fun showSorry()
}