package com.eafm.weather.model

import com.eafm.weather.repository.db.entities.CityDailyForecastEntity
import com.google.gson.annotations.SerializedName

data class CityDailyForecast(
    val city: City,
    @SerializedName("list") val forecastList: Array<Forecast>,
    @SerializedName("cod") val code: String = "",
    val message: String = ""
)

fun CityDailyForecast.toEntity(): List<CityDailyForecastEntity> {
    val entityList = mutableListOf<CityDailyForecastEntity>()
    this.forecastList.forEachIndexed { index, forecast ->
        entityList.add(
            CityDailyForecastEntity(
                name = this.city.name,
                main = forecast.information[0].main,
                description = forecast.information[0].description,
                icon = forecast.information[0].icon,
                minTemperature = forecast.temperature.minimum,
                maxTemperature = forecast.temperature.maximum,
                time = forecast.time
            )
        )
    }
    return entityList
}