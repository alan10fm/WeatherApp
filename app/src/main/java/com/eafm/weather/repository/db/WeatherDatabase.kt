package com.eafm.weather.repository.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.eafm.weather.repository.db.dao.WeatherDAO
import com.eafm.weather.repository.db.entities.CityCurrentWeatherEntity

@Database(entities = [CityCurrentWeatherEntity::class], version = 1, exportSchema = false)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun weatherDAO(): WeatherDAO

}
