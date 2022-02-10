package com.siuzannasmolianinova.calendar_diary.app

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.siuzannasmolianinova.calendar_diary.data.db.Database
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class CalendarApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Database.init(this)
        AndroidThreeTen.init(this)
    }
}
