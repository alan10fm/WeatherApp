package com.eafm.weather.repository

import android.arch.lifecycle.LiveData
import com.eafm.weather.repository.db.entities.CityCurrentWeatherEntity
import com.eafm.weather.repository.db.entities.CityDailyForecastEntity

interface Repository {

    fun getCurrentWeather(cityName: String): LiveData<List<CityCurrentWeatherEntity>>

    fun getDailyForecast(cityName: String): LiveData<List<CityDailyForecastEntity>>

}
