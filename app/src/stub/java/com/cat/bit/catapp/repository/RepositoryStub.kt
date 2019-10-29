package com.cat.bit.catapp.repository

import com.cat.bit.catapp.entity.Cats
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Single
import java.lang.reflect.Type

class RepositoryStub(val listCatsRepository: ListCatsRepository) :
    IListCatsRepository by listCatsRepository {
    override fun getListOfCat(): Single<List<Cats>> {
        val listType: Type = object : TypeToken<List<Cats>>() {}.type
        val listCats: List<Cats> = Gson().fromJson(JSONStub, listType)
        return Single.just(listCats)
    }
}

private const val JSONStub = """
    [
    {
        "breeds": [],
        "id": "2rq",
        "url": "https://cdn2.thecatapi.com/images/2rq.jpg",
        "width": 900,
        "height": 600
    },
    {
        "breeds": [],
        "id": "43m",
        "url": "https://cdn2.thecatapi.com/images/43m.gif",
        "width": 400,
        "height": 224
    },
    {
        "breeds": [],
        "id": "8jb",
        "url": "https://cdn2.thecatapi.com/images/8jb.jpg",
        "width": 900,
        "height": 728
    },
    {
        "breeds": [],
        "id": "9lr",
        "url": "https://cdn2.thecatapi.com/images/9lr.jpg",
        "width": 640,
        "height": 429
    },
    {
        "breeds": [],
        "id": "a3o",
        "url": "https://cdn2.thecatapi.com/images/a3o.jpg",
        "width": 640,
        "height": 426
    },
    {
        "breeds": [],
        "id": "b7c",
        "url": "https://cdn2.thecatapi.com/images/b7c.jpg",
        "width": 500,
        "height": 334
    },
    {
        "breeds": [],
        "id": "b9j",
        "url": "https://cdn2.thecatapi.com/images/b9j.jpg",
        "width": 500,
        "height": 333
    },
    {
        "breeds": [],
        "id": "cev",
        "url": "https://cdn2.thecatapi.com/images/cev.jpg",
        "width": 640,
        "height": 445
    },
    {
        "breeds": [],
        "id": "MTY0MTM2NQ",
        "url": "https://cdn2.thecatapi.com/images/MTY0MTM2NQ.gif",
        "width": 245,
        "height": 180
    },
    {
        "breeds": [],
        "id": "MjA2Nzg5OQ",
        "url": "https://cdn2.thecatapi.com/images/MjA2Nzg5OQ.jpg",
        "width": 500,
        "height": 333
    }
]
"""
