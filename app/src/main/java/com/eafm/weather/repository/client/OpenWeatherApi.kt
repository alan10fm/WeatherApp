package com.eafm.weather.repository.client

import com.eafm.weather.model.CityCurrentWeather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {

    @GET("/data/2.5/weather")
    fun getCurrentWeather(
        @Query("q") cityName: String,
        @Query("appid") key: String
    ): Call<CityCurrentWeather>

}
