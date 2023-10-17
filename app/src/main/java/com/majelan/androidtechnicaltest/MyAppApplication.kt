package com.majelan.androidtechnicaltest

import android.app.Application
import com.majelan.androidtechnicaltest.core.inject.PlayerModule
import com.majelan.androidtechnicaltest.core.inject.ViewModelModule
import com.majelan.androidtechnicaltest.data.DataModule
import com.majelan.androidtechnicaltest.domain.DomainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.ksp.generated.module
import timber.log.Timber

class MyAppApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@MyAppApplication)
            modules(
                DataModule().module,
                DomainModule().module,
                ViewModelModule().module,
                PlayerModule().module
            )
        }
    }
}