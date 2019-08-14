package com.cat.bit.catapp.dagger

import dagger.Component
import android.app.Application
import com.cat.bit.catapp.App
import com.cat.bit.catapp.ui.BookmarksFragment
import com.cat.bit.catapp.ui.ListFragment
import dagger.BindsInstance
import java.lang.annotation.Documented
import javax.inject.Qualifier
import javax.inject.Singleton


@Singleton
@Component(modules = [NetworkModule::class, AppModule::class, ListModule::class])
interface AppComponent {

    fun inject(listFragment: ListFragment)

    fun inject(bookmarksFragment: BookmarksFragment)

    fun inject(app: App)

    @Component.Builder
    interface Builder {
        fun build(): AppComponent
        @BindsInstance
        fun application(application: Application): Builder
    }
}