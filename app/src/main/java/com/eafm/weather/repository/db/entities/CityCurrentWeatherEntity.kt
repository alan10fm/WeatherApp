package com.eafm.weather.repository.db.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.eafm.weather.model.CityCurrentWeather
import com.eafm.weather.model.Information
import com.eafm.weather.model.Status

@Entity(tableName = "city_current_weather")
data class CityCurrentWeatherEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val main: String,
    val description: String,
    val icon: String,
    val temperature: Double,
    val errorCode: Int? = null
)

fun CityCurrentWeatherEntity.toObject() = CityCurrentWeather(
    id = this.id,
    name = this.name,
    status = Status(this.temperature),
    information = arrayOf(
        Information(
            this.main,
            this.description,
            this.icon
        )
    )
)

fun createErrorCityCurrentWeather(errorCode: Int) =
    CityCurrentWeatherEntity(0, "", "", "", "", 0.0, errorCode)
