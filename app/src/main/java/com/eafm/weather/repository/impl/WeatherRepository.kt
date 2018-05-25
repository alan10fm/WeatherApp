package com.eafm.weather.repository.impl

import android.arch.lifecycle.LiveData
import android.util.Log
import com.eafm.weather.exception.WeatherApiException
import com.eafm.weather.model.CityCurrentWeather
import com.eafm.weather.model.CityDailyForecast
import com.eafm.weather.model.toEntity
import com.eafm.weather.repository.Repository
import com.eafm.weather.repository.client.WeatherApi
import com.eafm.weather.repository.db.dao.WeatherDAO
import com.eafm.weather.repository.db.entities.CityCurrentWeatherEntity
import com.eafm.weather.repository.db.entities.CityDailyForecastEntity
import com.google.gson.Gson
import kotlinx.coroutines.experimental.launch
import org.json.JSONObject
import ru.gildor.coroutines.retrofit.awaitResponse
import java.net.UnknownHostException

class WeatherRepository(
    private val weatherApi: WeatherApi,
    private val weatherDAO: WeatherDAO
) : Repository {

    private val TAG = WeatherRepository::class.java.name
    private val gson = Gson()

    override fun getCurrentWeather(cityName: String): LiveData<List<CityCurrentWeatherEntity>> {
        launch {
            try {
                val response = weatherApi.getCurrentWeather(cityName).awaitResponse()
                if (response.code() != 200) {
                    throw Exception(getErrorMessage(response.errorBody()!!.string()))
                }
                weatherDAO.insert((response.body() as CityCurrentWeather).toEntity())
            } catch (exception: UnknownHostException) {
                Log.e(TAG, exception.message)
                throw WeatherApiException("Could not fetch results, check your network!!")
            } catch (exception: Exception) {
                Log.e(TAG, exception.message)
                throw WeatherApiException(exception.message!!)
            }
        }
        return weatherDAO.findCityCurrentWeatherByName(cityName)
    }

    override fun getDailyForecast(cityName: String): LiveData<List<CityDailyForecastEntity>> {
        launch {
            try {
                val response = weatherApi.getDailyForecast(cityName).awaitResponse()
                if (response.code() != 200) {
                    throw Exception(getErrorMessage(response.errorBody()!!.string()))
                }
                weatherDAO.insert((response.body() as CityDailyForecast).toEntity())
            } catch (exception: UnknownHostException) {
                Log.e(TAG, exception.message)
                throw WeatherApiException("Could not fetch results, check your network!!")
            } catch (exception: Exception) {
                Log.e(TAG, exception.message)
                throw WeatherApiException(exception.message!!)
            }
        }
        return weatherDAO.findCityDailyForecast(cityName)
    }

    private fun getErrorMessage(errorString: String?): String {
        if (errorString == null) {
            return "Unexpected error"
        }
        val jsonObject = JSONObject(errorString)
        return jsonObject.getString("message")
    }

}
