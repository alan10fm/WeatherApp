package com.eafm.weather.model

enum class ErrorCode(val code: Int) {
    NO_NETWORK(555), CITY_NOT_FOUND(404), UNEXPECTED_ERROR(500)
}