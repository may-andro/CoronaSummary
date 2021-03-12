package com.mayandro.coronasummary

import android.app.Application
import com.mayandro.coronasummary.di.uiModule
import com.mayandro.datasource.di.dataSourceModule
import com.mayandro.domain.di.domainModule
import com.mayandro.local.di.databaseModule
import com.mayandro.remote.di.remoteModule
import com.mayandro.utility.di.utilityModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CoronaApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        // Start Koin

        startKoin{
            androidLogger()
            androidContext(this@CoronaApplication)
            modules(
                listOf(
                    utilityModule,
                    databaseModule,
                    remoteModule,
                    dataSourceModule,
                    domainModule,
                    uiModule
                )
            )
        }
    }
}