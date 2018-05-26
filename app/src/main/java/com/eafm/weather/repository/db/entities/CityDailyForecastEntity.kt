package com.eafm.weather.repository.db.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.eafm.weather.model.*
import java.util.*

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
    val time: Long,
    val errorCode: Int? = null
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

fun createErrorCityDailyForecast(errorCode: Int) =
    CityDailyForecastEntity(0, "", "", "", "", 0.0, 0.0, 0, errorCode)

fun CityDailyForecastEntity.toDayOfWeek(): Int {
    val cal = GregorianCalendar()
    cal.time = Date(this.time * 1000)
    return cal.get(Calendar.DAY_OF_WEEK)
}
