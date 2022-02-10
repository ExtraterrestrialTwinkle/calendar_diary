package com.siuzannasmolianinova.calendar_diary.di

import android.app.Application
import androidx.room.Room
import com.siuzannasmolianinova.calendar_diary.data.db.CalendarDatabase
import com.siuzannasmolianinova.calendar_diary.data.db.EventDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun providesDatabase(application: Application): CalendarDatabase {
        Timber.d("providesDatabase")
        return Room.databaseBuilder(
            application,
            CalendarDatabase::class.java,
            CalendarDatabase.DB_NAME
        )
            .build()
    }

    @Provides
    fun providesEventDao(db: CalendarDatabase): EventDao {
        Timber.d("providesEventDao")
        return db.eventDao()
    }
}
