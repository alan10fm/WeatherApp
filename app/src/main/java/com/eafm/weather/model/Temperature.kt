package com.eafm.weather.model

import com.google.gson.annotations.SerializedName

data class Temperature(
    @SerializedName("min") val minimum: Double,
    @SerializedName("max") val maximum: Double
)