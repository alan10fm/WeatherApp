package com.eafm.weather.model

import com.google.gson.annotations.SerializedName

data class Status(
    @SerializedName("temp") val temperature: Double
)
