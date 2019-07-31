package com.cat.bit.catapp

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface CatApi {
    @GET("/v1/images/search")
    fun getCats(@Query("limit") limit: Int): Single<List<Cats>>
}