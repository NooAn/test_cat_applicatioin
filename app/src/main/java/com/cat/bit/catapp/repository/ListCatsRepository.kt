package com.cat.bit.catapp.repository

import com.cat.bit.catapp.CatApi
import com.cat.bit.catapp.Cats
import io.reactivex.Single
import retrofit2.Retrofit
import javax.inject.Inject


interface IListCatsRepository {
    fun getListOfCat(): Single<List<Cats>>
}

class ListCatsRepository @Inject constructor(val api: CatApi) : IListCatsRepository {
    private val LIMIT = 10

    override fun getListOfCat() = api.getCats(LIMIT)

}

