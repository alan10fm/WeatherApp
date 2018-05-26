package com.eafm.weather

import android.app.Application
import com.eafm.weather.module.repositoryModule
import com.eafm.weather.module.weatherModule
import org.koin.android.ext.android.startKoin

class WeatherApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(weatherModule, repositoryModule))
    }

}
