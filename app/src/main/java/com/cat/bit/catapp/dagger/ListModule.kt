package com.cat.bit.catapp.dagger

import com.cat.bit.catapp.network.CatApi
import com.cat.bit.catapp.interactor.ListInteractor
import com.cat.bit.catapp.repository.ListCatsRepository
import com.cat.bit.catapp.room.BookmarkDB
import com.cat.bit.catapp.room.BookmarkDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ListModule {
    @Singleton
    @Provides
    fun provideRepository(api: CatApi, db: BookmarkDao) = ListCatsRepository(api,db)

    @Singleton
    @Provides
    fun provideInteractor(repository: ListCatsRepository) =
        ListInteractor(repository)
}