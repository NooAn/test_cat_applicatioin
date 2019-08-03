package com.cat.bit.catapp

import android.app.Application
import com.cat.bit.catapp.dagger.AppComponent
import com.cat.bit.catapp.dagger.DaggerAppComponent

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
    }

    val component: AppComponent by lazy {
        DaggerAppComponent
            .builder()
            .application(this)
            .build()
    }
}