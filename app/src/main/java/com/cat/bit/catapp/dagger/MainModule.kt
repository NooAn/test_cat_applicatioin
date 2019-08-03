package com.cat.bit.catapp.dagger

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import android.content.SharedPreferences
import androidx.room.Room
import com.cat.bit.catapp.room.BookmarkDB


@Module
class AppModule {

    @Provides
    @Singleton
    fun providePreferences(application: Application): SharedPreferences {
        return application.getSharedPreferences("storage", Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    internal fun provideBookmarkDb(application: Application) =
        Room.databaseBuilder(application, BookmarkDB::class.java, "bookmarks")
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideBookmarkDao(db: BookmarkDB) = db.getBookmarkDao()

}