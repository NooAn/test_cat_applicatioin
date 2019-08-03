package com.cat.bit.catapp.dagger

import android.app.Application
import com.cat.bit.catapp.*
import com.cat.bit.catapp.network.CatApi
import com.cat.bit.catapp.network.ConnectivityInteractor
import com.cat.bit.catapp.network.NetworkConnectionHelper
import com.cat.bit.catapp.network.RequestInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideInterceptor() = RequestInterceptor()

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }

    @Singleton
    @Provides
    fun provideOkHttp(catInterceptor: RequestInterceptor, loggingInterceptor: HttpLoggingInterceptor) =
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .addInterceptor(catInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient) = Retrofit.Builder()
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .baseUrl(CAT_URL)
        .build()
        .create(CatApi::class.java)

    @Singleton
    @Provides
    internal fun provideNetworkConnectionHelper(app: Application) =
        NetworkConnectionHelper(app)


    @Singleton
    @Provides
    internal fun provideConnectivity(networkConnectionHelper: NetworkConnectionHelper) =
        ConnectivityInteractor(networkConnectionHelper)


}