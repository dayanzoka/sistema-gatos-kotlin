package com.example.sistema_cuidadogatos

import android.app.Application
import com.example.sistema_cuidadogatos.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CuiGatoApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@CuiGatoApplication)
            modules(appModule)
        }
    }
}

