package com.eafm.weather.repository.client

import com.eafm.weather.model.CityCurrentWeather
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherApi {

    private val openWeatherApi: OpenWeatherApi
    private val openWeatherKey = "97d69fbbd75bb45bbfebaf7284e4424b"

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        openWeatherApi = retrofit.create(OpenWeatherApi::class.java)
    }

    fun getCurrentWeather(
        cityName: String
    ): Call<CityCurrentWeather> = openWeatherApi.getCurrentWeather(cityName, openWeatherKey)

}
