package com.eafm.weather.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.eafm.weather.repository.db.entities.CityDailyForecastEntity
import com.eafm.weather.repository.db.entities.toDayOfWeek
import com.eafm.weather.weatherapp.R
import com.squareup.picasso.Picasso
import java.util.*

class WeatherAdapter(
    private var dailyForecast: List<CityDailyForecastEntity>,
    private val context: Context
) :
    RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.forecast_card, parent, false)
        return ViewHolder(v);
    }

    override fun getItemCount() = dailyForecast.size

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val forecast = dailyForecast[position]
        holder?.dayText?.text = getDayString(forecast.toDayOfWeek())
        holder?.minText?.text = forecast.minTemperature.toString()
        holder?.maxText?.text = forecast.maxTemperature.toString()
        Picasso.get().load("http://openweathermap.org/img/w/${forecast.icon}.png")
            .into(holder?.icon)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dayText = itemView.findViewById<TextView>(R.id.day_text)
        val minText = itemView.findViewById<TextView>(R.id.min_text)
        val maxText = itemView.findViewById<TextView>(R.id.max_text)
        val icon = itemView.findViewById<ImageView>(R.id.forecast_icon)
    }

    private fun getDayString(dayOfWeek: Int) = when (dayOfWeek) {
        Calendar.MONDAY -> context.getString(R.string.monday)
        Calendar.TUESDAY -> context.getString(R.string.tuesday)
        Calendar.WEDNESDAY -> context.getString(R.string.wednesday)
        Calendar.THURSDAY -> context.getString(R.string.thursday)
        Calendar.FRIDAY -> context.getString(R.string.friday)
        Calendar.SATURDAY -> context.getString(R.string.saturday)
        Calendar.SUNDAY -> context.getString(R.string.sunday)
        else -> ""
    }

    fun populateView(dailyForecast: List<CityDailyForecastEntity>) {
        if (this.dailyForecast.isEmpty()) {
            this.dailyForecast = dailyForecast
        } else {
            (this.dailyForecast as ArrayList).addAll(dailyForecast)
        }
    }

    fun clear() {
        if (dailyForecast.isNotEmpty()) {
            (dailyForecast as ArrayList).clear()
            notifyDataSetChanged()
        }
    }

}