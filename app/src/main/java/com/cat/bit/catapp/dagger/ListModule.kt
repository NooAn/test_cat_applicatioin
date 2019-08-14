package com.cat.bit.catapp.dagger

import com.cat.bit.catapp.interactor.IListInteractor
import com.cat.bit.catapp.interactor.ListInteractor
import com.cat.bit.catapp.repository.IListCatsRepository
import com.cat.bit.catapp.repository.ListCatsRepository
import dagger.Binds
import dagger.Module


@Module
interface ListModule {
    @Binds
    fun provideRepository(repository: ListCatsRepository): IListCatsRepository

    @Binds
    fun provideInteractor(interactor: ListInteractor): IListInteractor
}
