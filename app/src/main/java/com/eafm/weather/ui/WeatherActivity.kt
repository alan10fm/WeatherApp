package com.eafm.weather.ui

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.eafm.weather.repository.db.entities.CityCurrentWeatherEntity
import com.eafm.weather.viewmodel.WeatherViewModel
import com.eafm.weather.weatherapp.R
import org.koin.android.architecture.ext.viewModel

class WeatherActivity : AppCompatActivity() {

    val model by viewModel<WeatherViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        model.getCurrentWeather("London").observe(
            this@WeatherActivity,
            Observer<List<CityCurrentWeatherEntity>> { cityCurrentWeather ->
                Toast.makeText(
                    this@WeatherActivity,
                    cityCurrentWeather.toString(),
                    Toast.LENGTH_LONG
                ).show()
            })
    }
}
