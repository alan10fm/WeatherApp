package com.eafm.weather.repository

import android.arch.lifecycle.LiveData
import com.eafm.weather.repository.db.entities.CityCurrentWeatherEntity

interface Repository {

    fun getCurrentWeather(cityName: String): LiveData<List<CityCurrentWeatherEntity>>

}
