package com.eafm.weather.repository

import android.arch.lifecycle.LiveData
import android.util.Log
import com.eafm.weather.model.CityCurrentWeather
import com.eafm.weather.model.toEntity
import com.eafm.weather.repository.client.WeatherApi
import com.eafm.weather.repository.db.dao.WeatherDAO
import com.eafm.weather.repository.db.entities.CityCurrentWeatherEntity
import kotlinx.coroutines.experimental.launch
import ru.gildor.coroutines.retrofit.awaitResponse

class WeatherRepository(
    private val weatherApi: WeatherApi,
    private val weatherDAO: WeatherDAO
) : Repository {

    private val TAG = WeatherRepository::class.java.name

    override fun getCurrentWeather(cityName: String): LiveData<List<CityCurrentWeatherEntity>> {
        launch {
            try {
                val response = weatherApi.getCurrentWeather(cityName).awaitResponse()
                weatherDAO.insert((response.body() as CityCurrentWeather).toEntity())
            } catch (exception: Exception) {
                Log.e(TAG, exception.message)
            }
        }
        return weatherDAO.findCityCurrentWeatherByName(cityName)
    }

}
