package com.eafm.weather.model

import com.google.gson.annotations.SerializedName

data class Forecast(
    @SerializedName("dt") val time: Long,
    @SerializedName("temp") val temperature: Temperature,
    @SerializedName("weather") val information: Array<Information>
)
