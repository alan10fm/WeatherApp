package com.eafm.weather.repository.impl

import android.arch.lifecycle.MutableLiveData
import com.eafm.weather.model.CityCurrentWeather
import com.eafm.weather.model.CityDailyForecast
import com.eafm.weather.model.ErrorCode
import com.eafm.weather.model.toEntity
import com.eafm.weather.repository.Repository
import com.eafm.weather.repository.client.WeatherApi
import com.eafm.weather.repository.db.dao.WeatherDAO
import com.eafm.weather.repository.db.entities.CityCurrentWeatherEntity
import com.eafm.weather.repository.db.entities.CityDailyForecastEntity
import com.eafm.weather.repository.db.entities.createErrorCityCurrentWeather
import com.eafm.weather.repository.db.entities.createErrorCityDailyForecast
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.net.UnknownHostException

class WeatherRepository(
    private val weatherApi: WeatherApi,
    private val weatherDAO: WeatherDAO
) : Repository {

    private val TAG = WeatherRepository::class.java.name
    var liveCurrentWeather: MutableLiveData<List<CityCurrentWeatherEntity>> = MutableLiveData()
    var liveDailyForecast: MutableLiveData<List<CityDailyForecastEntity>> = MutableLiveData()


    override fun getCurrentWeather(cityName: String): MutableLiveData<List<CityCurrentWeatherEntity>> {
        liveCurrentWeather = MutableLiveData()
        doAsync {
            try {
                val result = weatherApi.getCurrentWeather(cityName).execute()
                if (result.isSuccessful) {
                    val currentWeather: CityCurrentWeather? = result.body()
                    if (currentWeather != null) {
                        val weatherEntity = currentWeather.toEntity()
                        weatherDAO.insert(weatherEntity)
                        uiThread {
                            liveCurrentWeather.value = listOf(weatherEntity)
                        }
                    }
                } else {
                    uiThread {
                        val errorCode =
                            if (result.code() == 404) ErrorCode.CITY_NOT_FOUND else ErrorCode.UNEXPECTED_ERROR
                        liveCurrentWeather.value =
                                listOf(createErrorCityCurrentWeather(errorCode.code))
                    }
                }
            } catch (exception: UnknownHostException) {
                val currentWeatherLocal = weatherDAO.findCityCurrentWeatherByName(cityName)
                uiThread {
                    if (currentWeatherLocal.isNotEmpty()) {
                        liveCurrentWeather.value = currentWeatherLocal
                    } else {
                        liveCurrentWeather.value =
                                listOf(createErrorCityCurrentWeather(ErrorCode.NO_NETWORK.code))
                    }
                }
            } catch (exception: Exception) {
                uiThread {
                    liveCurrentWeather.value =
                            listOf(createErrorCityCurrentWeather(ErrorCode.UNEXPECTED_ERROR.code))
                }
            }
        }
        liveCurrentWeather.value = emptyList()
        return liveCurrentWeather
    }

    override fun getDailyForecast(cityName: String): MutableLiveData<List<CityDailyForecastEntity>> {
        liveDailyForecast = MutableLiveData()
        doAsync {
            try {
                val result = weatherApi.getDailyForecast(cityName).execute()
                if (result.isSuccessful) {
                    val dailyForecast: CityDailyForecast? = result.body()
                    if (dailyForecast != null) {
                        val forecastEntity = dailyForecast.toEntity()
                        weatherDAO.insert(forecastEntity)
                        uiThread {
                            liveDailyForecast.value = forecastEntity
                        }
                    }
                } else {
                    uiThread {
                        val errorCode =
                            if (result.code() == 404) ErrorCode.CITY_NOT_FOUND else ErrorCode.UNEXPECTED_ERROR
                        liveDailyForecast.value =
                                listOf(createErrorCityDailyForecast(errorCode.code))
                    }
                }
            } catch (exception: UnknownHostException) {
                val forecastCity = weatherDAO.findCityDailyForecast(cityName)
                uiThread {
                    if (forecastCity.isNotEmpty()) {
                        liveDailyForecast.value = forecastCity
                    } else {
                        liveDailyForecast.value =
                                listOf(createErrorCityDailyForecast(ErrorCode.NO_NETWORK.code))
                    }
                }
            } catch (exception: Exception) {
                uiThread {
                    liveDailyForecast.value =
                            listOf(createErrorCityDailyForecast(ErrorCode.UNEXPECTED_ERROR.code))
                }
            }
        }
        liveDailyForecast.value = emptyList()
        return liveDailyForecast
    }

}
