package com.eafm.weather.ui

import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.eafm.weather.model.ErrorCode
import com.eafm.weather.repository.db.entities.CityCurrentWeatherEntity
import com.eafm.weather.repository.db.entities.CityDailyForecastEntity
import com.eafm.weather.viewmodel.WeatherViewModel
import com.eafm.weather.weatherapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_weather.*
import org.koin.android.architecture.ext.viewModel


class WeatherActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    val model by viewModel<WeatherViewModel>()
    val adapter = WeatherAdapter(listOf(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        search_view.setOnQueryTextListener(this)
        forecast_recycler.layoutManager = LinearLayoutManager(this)
        forecast_recycler.adapter = adapter
    }

    override fun onQueryTextSubmit(text: String?): Boolean {
        if (text != null && text.isNotBlank()) {
            hideKeyboard()
            loadCityWeather(text)
            weather_card.visibility = View.GONE
            error_linear.visibility = View.GONE
            adapter.clear()
            return true
        }
        return false
    }

    override fun onQueryTextChange(text: String?): Boolean {
        return false
    }

    private fun loadCityWeather(cityName: String) {
        model.getDailyForecast(cityName).observe(
            this@WeatherActivity,
            Observer<List<CityDailyForecastEntity>> { dailyForecast ->
                processDailyForecast(dailyForecast)
            })

        model.getCurrentWeather(cityName).observe(
            this@WeatherActivity,
            Observer<List<CityCurrentWeatherEntity>> { currentWeather ->
                processCurrentWeather(currentWeather)
            })
    }

    private fun processCurrentWeather(currentWeather: List<CityCurrentWeatherEntity>?) {
        if (currentWeather == null || currentWeather.isEmpty()) {
            return
        }
        val weather = currentWeather[0]
        if (weather.errorCode != null) {
            processError(weather.errorCode)
            return
        }
        main_text.text = weather.main
        description_text.text = weather.description
        temperature_text.text = weather.temperature.toString()
        Picasso.get().load("http://openweathermap.org/img/w/${weather.icon}.png").into(weather_icon)
        weather_card.visibility = View.VISIBLE
    }

    private fun processDailyForecast(dailyForecast: List<CityDailyForecastEntity>?) {
        if (dailyForecast == null || dailyForecast.isEmpty()) {
            return
        }
        val forecast = dailyForecast[0]
        if (forecast.errorCode != null) {
            processError(forecast.errorCode)
            return
        }
        adapter.clear()
        adapter.populateView(dailyForecast)
        forecast_recycler.visibility = View.VISIBLE
    }

    private fun processError(errorCode: Int) {
        error_linear.visibility = View.VISIBLE
        error_text.text = getErrorText(errorCode)
    }

    private fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun getErrorText(errorCode: Int) = when (errorCode) {
        ErrorCode.NO_NETWORK.code -> getString(R.string.no_network)
        ErrorCode.CITY_NOT_FOUND.code -> getString(R.string.not_found)
        ErrorCode.UNEXPECTED_ERROR.code -> getString(R.string.unexpected_error)
        else -> throw(Exception("If you ended up here we are in trouble"))
    }

}
