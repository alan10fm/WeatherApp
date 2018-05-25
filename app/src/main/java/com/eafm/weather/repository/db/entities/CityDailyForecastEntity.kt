package com.eafm.weather.repository.db.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.eafm.weather.model.*

@Entity(tableName = "city_daily_forecast")
data class CityDailyForecastEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val main: String,
    val description: String,
    val icon: String,
    val minTemperature: Double,
    val maxTemperature: Double,
    val time: Long
)

fun CityDailyForecastEntity.toObject() = CityDailyForecast(
    city = City(name = this.name),
    forecastList = arrayOf(
        Forecast(
            time = this.time,
            temperature = Temperature(minimum = minTemperature, maximum = maxTemperature),
            information = arrayOf(
                Information(
                    main = this.main,
                    description = this.description,
                    icon = this.icon
                )
            )
        )
    )
)