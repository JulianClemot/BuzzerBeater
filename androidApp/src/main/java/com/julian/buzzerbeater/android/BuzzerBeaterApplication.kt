package com.julian.buzzerbeater.android

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import timber.log.Timber
import timber.log.Timber.Forest.plant

class BuzzerBeaterApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        plant(Timber.DebugTree())

        startKoin {
            // Log Koin into Android logger
            androidLogger()
            // Reference Android context
            androidContext(this@BuzzerBeaterApplication)
            // Load modules
            modules(appModule)
        }
    }
}