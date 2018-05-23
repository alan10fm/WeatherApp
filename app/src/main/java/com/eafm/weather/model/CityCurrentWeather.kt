package com.eafm.weather.model

import com.eafm.weather.repository.db.entities.CityCurrentWeatherEntity
import com.google.gson.annotations.SerializedName

data class CityCurrentWeather(
    val id: Int,
    val name: String,
    @SerializedName("main") val status: Status,
    @SerializedName("weather") val information: Array<Information>,
    @SerializedName("cod") val code: String = "",
    val message: String = ""
)

fun CityCurrentWeather.toEntity() = CityCurrentWeatherEntity(
    id = this.id,
    name = this.name,
    main = this.information.get(0).main,
    description = this.information.get(0).description,
    icon = this.information.get(0).icon,
    temperature = this.status.temperature
)
