package com.eafm.weather.repository.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.eafm.weather.repository.db.entities.CityCurrentWeatherEntity

@Dao
interface WeatherDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cityCurrentWeatherEntity: CityCurrentWeatherEntity)

    @Query("SELECT * FROM city_current_weather WHERE name LIKE :name LIMIT 1")
    fun findCityCurrentWeatherByName(name: String): LiveData<List<CityCurrentWeatherEntity>>

}
