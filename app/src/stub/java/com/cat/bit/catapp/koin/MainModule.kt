package com.cat.bit.catapp.koin

import android.content.Context
import androidx.room.Room
import com.cat.bit.catapp.CAT_URL
import com.cat.bit.catapp.interactor.ListInteractor
import com.cat.bit.catapp.network.CatApi
import com.cat.bit.catapp.network.ConnectivityInteractor
import com.cat.bit.catapp.network.NetworkConnectionHelper
import com.cat.bit.catapp.network.RequestInterceptor
import com.cat.bit.catapp.presenter.BookmarksPresenter
import com.cat.bit.catapp.presenter.ListPresenter
import com.cat.bit.catapp.repository.IListCatsRepository
import com.cat.bit.catapp.repository.ListCatsRepository
import com.cat.bit.catapp.repository.RepositoryStub
import com.cat.bit.catapp.room.BookmarkDB
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {
    single { androidApplication().getSharedPreferences("storage", Context.MODE_PRIVATE) }
    single {
        Room.databaseBuilder(androidApplication(), BookmarkDB::class.java, "bookmark_db")
            .fallbackToDestructiveMigration()
            .build()
    }
    single { get<BookmarkDB>().getBookmarkDao() }

    //List
    single { ListCatsRepository(get(), get()) }
    single<IListCatsRepository> { RepositoryStub(get())}
    single { ListInteractor(get()) }
    factory { ListPresenter(get(), get()) }
    factory { BookmarksPresenter(get()) }

    //Network
    single { RequestInterceptor() }
    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
    }
    single {
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .addInterceptor(get<RequestInterceptor>())
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }
    single {
        Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .baseUrl(CAT_URL)
            .build()
            .create(CatApi::class.java)
    }
    single { NetworkConnectionHelper(androidApplication()) }
    single { ConnectivityInteractor(get()) }
}