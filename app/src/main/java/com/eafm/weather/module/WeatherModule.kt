package com.eafm.weather.module

import android.arch.persistence.room.Room
import com.eafm.weather.repository.Repository
import com.eafm.weather.repository.WeatherRepository
import com.eafm.weather.repository.client.WeatherApi
import com.eafm.weather.repository.db.WeatherDatabase
import com.eafm.weather.viewmodel.WeatherViewModel
import org.koin.android.architecture.ext.viewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.applicationContext

val forecastModule = applicationContext {

    viewModel { WeatherViewModel(get()) }

}

val repositoryModule = applicationContext {

    bean { WeatherRepository(get(), get()) as Repository }
    bean { WeatherApi() }
    bean {
        Room.databaseBuilder(androidApplication(), WeatherDatabase::class.java, "weather-db")
            .allowMainThreadQueries()
            .build()
    }
    bean { get<WeatherDatabase>().weatherDAO() }

}
