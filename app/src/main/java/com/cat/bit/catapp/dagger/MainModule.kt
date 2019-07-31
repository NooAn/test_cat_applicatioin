package com.cat.bit.catapp.dagger

import android.accounts.AccountManager
import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import android.content.SharedPreferences


@Module
class AppModule {

//    @Singleton
//    @Provides
//    internal fun provideApplication(application: Application): Application {
//        return application
//    }

    @Provides
    @Singleton
    fun providePreferences(application: Application): SharedPreferences {
        return application.getSharedPreferences("storage", Context.MODE_PRIVATE)
    }
}