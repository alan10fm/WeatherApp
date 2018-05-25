package com.eafm.weather.repository.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.eafm.weather.repository.db.entities.CityCurrentWeatherEntity
import com.eafm.weather.repository.db.entities.CityDailyForecastEntity

@Dao
interface WeatherDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cityCurrentWeatherEntity: CityCurrentWeatherEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(listCityDailyForecastEntity: List<CityDailyForecastEntity>)

    @Query("SELECT * FROM city_current_weather WHERE name LIKE :name LIMIT 1")
    fun findCityCurrentWeatherByName(name: String): LiveData<List<CityCurrentWeatherEntity>>

    @Query("SELECT * FROM city_daily_forecast WHERE name LIKE :name")
    fun findCityDailyForecast(name: String): LiveData<List<CityDailyForecastEntity>>

}
