package com.eafm.weather.viewmodel

import android.arch.lifecycle.ViewModel
import com.eafm.weather.repository.Repository

class WeatherViewModel(private val repository: Repository) : ViewModel() {

    fun getCurrentWeather(cityName: String) = repository.getCurrentWeather(cityName)

}
