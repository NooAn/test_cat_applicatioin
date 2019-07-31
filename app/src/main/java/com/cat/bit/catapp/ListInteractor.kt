package com.cat.bit.catapp

import com.cat.bit.catapp.repository.ListCatsRepository
import javax.inject.Inject


class ListInteractor @Inject constructor(private val repository: ListCatsRepository) {
    private val DEFAULT_ERROR_IMAGE =
        "https://img.medscape.com/thumbnail_library/dt_160608_error_sign_800x600.jpg"

    fun getListOfCat() = repository.getListOfCat().cache().onErrorReturnItem(listOf(Cats().apply {
        url = DEFAULT_ERROR_IMAGE
    }))

}