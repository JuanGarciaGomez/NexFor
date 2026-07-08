package com.project.nexfor

import android.app.Application
import com.project.nexfor.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

/**
 * Custom Android [Application] class to initialize Koin.
 */
class NexForApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin {
            // Log Koin events in Android Logcat
            androidLogger(Level.DEBUG)
            // Reference Android context
            androidContext(this@NexForApplication)
        }
    }
}
