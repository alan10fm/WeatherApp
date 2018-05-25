package com.eafm.weather.repository.client

import com.eafm.weather.model.CityCurrentWeather
import com.eafm.weather.model.CityDailyForecast
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherApi {

    private val openWeatherApi: OpenWeatherApi
    private val OPEN_WEATHER_KEY = "adb4503a31093fed77c0a5f39d4c512b"
    private val OPEN_WEATHER_UNITS = "metric"

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        openWeatherApi = retrofit.create(OpenWeatherApi::class.java)
    }

    fun getCurrentWeather(
        cityName: String
    ): Call<CityCurrentWeather> =
        openWeatherApi.getCurrentWeather(cityName, OPEN_WEATHER_UNITS, OPEN_WEATHER_KEY)

    fun getDailyForecast(
        cityName: String
    ): Call<CityDailyForecast> =
        openWeatherApi.getDailyForecast(cityName, OPEN_WEATHER_UNITS, OPEN_WEATHER_KEY)

}
