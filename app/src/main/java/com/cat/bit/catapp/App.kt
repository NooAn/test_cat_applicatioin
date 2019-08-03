package com.cat.bit.catapp

import android.app.Application
import com.cat.bit.catapp.dagger.AppComponent
import com.cat.bit.catapp.dagger.DaggerAppComponent
import com.facebook.stetho.Stetho

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
        Stetho.initializeWithDefaults(this)
    }

    val component: AppComponent by lazy {
        DaggerAppComponent
            .builder()
            .application(this)
            .build()
    }
}