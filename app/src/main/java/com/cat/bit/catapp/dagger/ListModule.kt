package com.cat.bit.catapp.dagger

import com.cat.bit.catapp.network.CatApi
import com.cat.bit.catapp.interactor.ListInteractor
import com.cat.bit.catapp.repository.ListCatsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ListModule {
    @Singleton
    @Provides
    fun provideRepository(api: CatApi) = ListCatsRepository(api)

    @Singleton
    @Provides
    fun provideInteractor(repository: ListCatsRepository) =
        ListInteractor(repository)
}